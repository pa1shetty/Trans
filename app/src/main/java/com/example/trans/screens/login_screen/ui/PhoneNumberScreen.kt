package com.example.trans.screens.login_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.trans.R
import com.example.trans.databinding.PhoneNumberScreenBinding
import com.example.trans.utillity.firebase.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class PhoneNumberScreen : Fragment() {
    private val vm: LoginVM by navGraphViewModels(R.id.login_graph) { defaultViewModelProviderFactory }

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: PhoneNumberScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhoneNumberScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        setUpClick()

    }

    private fun listeners() {
        binding.etNumber.doOnTextChanged { text, _, _, _ ->
            vm.phoneNumber = text.toString()
        }
    }

    private fun setUpClick() {
        binding.btnProceed.setOnClickListener {
            startPhoneNumberVerification(("+91") + vm.phoneNumber)
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        firebaseAuth.startPhoneNumberVerification(
            phoneNumber, vm.resendingToken,
            onVerificationCompleted = { credential ->
                firebaseAuth.signInWithPhoneAuthCredential(
                    credential, signInSuccessFull = { firebaseUSer ->
                        userLoggedIn(firebaseUSer)
                    }, invalidCredential = {
                        Toast.makeText(requireContext(), "Invalid Credentials!!", Toast.LENGTH_LONG)
                            .show()
                    }, error = {
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                            .show()

                    })
            },
            onVerificationFailed = {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show()
            }, onCodeSent =
            { verificationId, forceResendingToken ->
                vm.verificationId = verificationId
                vm.resendingToken = forceResendingToken
                navigateTo(PhoneNumberScreenDirections.actionPhoneNumberScreenToOtpScreen())
            })
    }

    private fun userLoggedIn(firebaseUSer: FirebaseUser) {
        lifecycleScope.launch(Dispatchers.IO) {
            vm.userLoggedIn(firebaseUSer)
            withContext(Dispatchers.Main) {
                navigateTo(PhoneNumberScreenDirections.actionGlobalHomeScreen())
            }
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        lifecycleScope.launch {
            findNavController().navigate(navDirections)
        }
    }
}