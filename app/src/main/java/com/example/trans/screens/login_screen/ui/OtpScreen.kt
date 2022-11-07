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
import com.example.trans.databinding.OtpScreenBinding
import com.example.trans.utillity.firebase.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class OtpScreen : Fragment() {
    private val vm: LoginVM by navGraphViewModels(R.id.login_graph) { defaultViewModelProviderFactory }

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: OtpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OtpScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener()
        setUpClick()

    }

    private fun setUpClick() {
        binding.btnConfirm.setOnClickListener {
            confirmButtonClick()
        }
    }

    private fun confirmButtonClick() {
        firebaseAuth.signInWithPhoneAuthCredential(
            firebaseAuth.verifyPhoneNumberWithCode(vm.verificationId, vm.otp),
            signInSuccessFull = { firebaseUSer ->
                userLoggedIn(firebaseUSer)
            },
            invalidCredential = {
                Toast.makeText(requireContext(), "Invalid Credentials!!", Toast.LENGTH_LONG)
                    .show()
            },
            error = {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show()
            })
    }

    private fun userLoggedIn(firebaseUSer: FirebaseUser) {
        lifecycleScope.launch(Dispatchers.IO) {
            vm.userLoggedIn(firebaseUSer)
            withContext(Dispatchers.Main) {
                navigateTo(OtpScreenDirections.actionGlobalHomeScreen())
            }
        }
    }

    private fun listener() {
        binding.etOtp.doOnTextChanged { text, _, _, _ ->
            vm.otp = text.toString()
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        lifecycleScope.launch {
            findNavController().navigate(navDirections)
        }
    }
}