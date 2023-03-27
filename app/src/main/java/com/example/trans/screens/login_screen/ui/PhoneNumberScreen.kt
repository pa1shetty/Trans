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
import com.example.trans.network.Enums.RequestStatus
import com.example.trans.screens.setup_screen.utils.CustomButton
import com.example.trans.utillity.UtilsClassUI
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
    private lateinit var customButton: CustomButton


    private lateinit var binding: PhoneNumberScreenBinding

    @Inject
    lateinit var utilsClassUI: UtilsClassUI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = PhoneNumberScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        setProceedButton()
        setUpObserver()
        setUpClick()
    }

    private fun listeners() {
        binding.etNumber.doOnTextChanged { text, _, _, _ ->
            vm.phoneNumber = text.toString()
        }
    }

    private fun setUpClick() {
        binding.btnProceed.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                vm.phoneNumber = binding.etNumber.text.toString()
                vm.checkIfUserIsWhitelisted()
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        utilsClassUI.showLoadingUI(binding.loadingLayout.root)
        firebaseAuth.startPhoneNumberVerification(phoneNumber,
            vm.resendingToken,
            onVerificationCompleted = { credential ->
                firebaseAuth.signInWithPhoneAuthCredential(credential,
                    signInSuccessFull = { firebaseUSer ->
                        utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                        userLoggedIn(firebaseUSer)
                    },
                    invalidCredential = {
                        utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                        Toast.makeText(requireContext(), "Invalid Credentials!!", Toast.LENGTH_LONG)
                            .show()
                    },
                    error = {
                        utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()

                    })
            },
            onVerificationFailed = {
                utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                Toast.makeText(requireContext(), "Otp Verification Failed", Toast.LENGTH_LONG)
                    .show()
            },
            onCodeSent = { verificationId, forceResendingToken ->
                vm.verificationId = verificationId
                vm.resendingToken = forceResendingToken
                utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                navigateTo(PhoneNumberScreenDirections.actionPhoneNumberScreenToOtpScreen())
            })
    }

    private fun userLoggedIn(firebaseUSer: FirebaseUser) {
        lifecycleScope.launch(Dispatchers.IO) {
            vm.userLoggedIn(firebaseUSer)
            withContext(Dispatchers.Main) {
                utilsClassUI.toastMessage("Firebase login done.")

                // navigateTo(PhoneNumberScreenDirections.actionGlobalHomeScreen())
            }
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        lifecycleScope.launch {
            findNavController().navigate(navDirections)
        }
    }

    private fun setUpObserver() {
        vm.ifUserIsWhitelistedResponseLiveData.observe(viewLifecycleOwner) { loginState ->
            if (loginState) {
                startPhoneNumberVerification(("+91") + vm.phoneNumber)
            } else {
                utilsClassUI.showAlertDialog()
            }
        }
        vm.requestStatusLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                RequestStatus.REQ_PROGRESS -> utilsClassUI.showLoadingUI(binding.loadingLayout.root)
                else -> {
                    utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                }
            }
        }
    }

    private fun setProceedButton() {
        customButton = CustomButton(
            binding.cvProceed,
            binding.pbLogin,
            requireActivity(),
            binding.btnProceed,
        )
        customButton.enable()
    }

}