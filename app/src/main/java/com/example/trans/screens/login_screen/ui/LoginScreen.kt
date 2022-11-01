package com.example.trans.screens.login_screen.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.trans.R
import com.example.trans.databinding.LoginScreenBinding
import com.example.trans.utillity.firebase.FirebaseAuth
import com.example.trans.utillity.google.GoogleSignSetup
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginScreen : Fragment() {
    lateinit var binding: LoginScreenBinding
    private val vm: LoginVM by navGraphViewModels(R.id.login_graph) { defaultViewModelProviderFactory }

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var googleSignSetup: GoogleSignSetup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClick()
    }

    private fun setUpClick() {
        binding.btnMblLogin.setOnClickListener {
            navigateTo(LoginScreenDirections.actionLoginScreenToPhoneNumberScreen())
        }
        binding.btnGoogleLogin.setOnClickListener {
            Log.d(TAG, "setUpClick: ")
            googleSignInStart()
        }
    }


    private val TAG = "test123"
    private var showOneTapUI = true
    private fun googleSignInStart() {
        googleSignSetup.beginSignIn(onSuccess = { intentSenderRequest ->
            resultLauncher.launch(intentSenderRequest)
        }, onError = {
            Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_LONG).show()
        })

    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->

        try {
            googleSignSetup.getGoogleCredential(result.data).googleIdToken?.let {
                firebaseAuthWithGoogle(
                    it
                )
            } ?: errorToast()
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    errorToast()
                    showOneTapUI = false
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    errorToast()
                }
                else -> {
                    errorToast()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        firebaseAuth.signInWithPhoneAuthCredential(
            firebaseAuth.getGoogleAuthCredential(idToken),
            signInSuccessFull = { firebaseUSer ->
                userLoggedIn(firebaseUSer)
            },
            invalidCredential = {
                errorToast()
            },
            error = {
                errorToast()
            })
    }

    private fun userLoggedIn(firebaseUSer: FirebaseUser) {
        lifecycleScope.launch(Dispatchers.Main) {
            vm.userLoggedIn(firebaseUSer)
            navigateTo(LoginScreenDirections.actionGlobalHomeScreen())
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                findNavController().navigate(navDirections)
            }
        }
    }

    private fun errorToast() {
        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
            .show()
    }
}