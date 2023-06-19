package com.example.trans.screens.login_screen.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.trans.databinding.FragmentRegistrationBinding
import com.example.trans.network.enums.RequestStatus
import com.example.trans.network.responses.UserDetails
import com.example.trans.screens.login_screen.vm.RegistrationVM
import com.example.trans.utillity.LocationHelper
import com.example.trans.utillity.UtilClass
import com.example.trans.utillity.UtilsClassUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private val vm by viewModels<RegistrationVM>()

    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var utilsClassUI: UtilsClassUI

    @Inject
    lateinit var utilsClass: UtilClass
    private lateinit var binding: FragmentRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationHelper.checkLocationPermission()
        setupClick()
        setUpObserver()

    }

    private fun setupClick() {
        binding.btnSave.setOnClickListener {
            handleBtnSaveClick()
        }
    }

    private fun handleBtnSaveClick() {
        if (isDataValid()) {
            locationHelper.getCurrentLocation(onLocationReceived = { location ->
                vm.sendUserData(
                    UserDetails(
                        usrAddrs = binding.tvShopAddress.text.toString(),
                        usrName = binding.tvUserName.text.toString(),
                        usrShopName = binding.tvShopName.text.toString(),
                        usrLong = location.longitude,
                        userLat = location.latitude
                    )
                )
            },
                error = {
                    utilsClassUI.toastMessage("Please give location permission in setting.")
                })
        } else {
            utilsClassUI.toastMessage("Please enters all data's")
        }
    }

    private fun setUpObserver() {
        vm.requestStatusLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                RequestStatus.REQ_PROGRESS -> utilsClassUI.showLoadingUI(binding.loadingLayout.root)
                RequestStatus.REQ_SUCCESS -> navigateTo(RegistrationFragmentDirections.actionRegistrationFragmentToHomeScreen())
                else -> {
                    utilsClassUI.toastMessage("Please Retry")
                    utilsClassUI.hideLoadingUI(binding.loadingLayout.root)
                }
            }
        }
        vm.userPhoneNumber.observe(viewLifecycleOwner) { phoneNumber ->
            binding.tvPhoneNumber.setText(phoneNumber)
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        lifecycleScope.launch {
            findNavController().navigate(navDirections)
        }
    }

    private fun isDataValid() = utilsClass.validateData(
        arrayListOf(
            binding.tvPhoneNumber.text.toString(),
            binding.tvShopAddress.text.toString(),
            binding.tvShopName.text.toString(),
            binding.tvShopName.text.toString(),
            binding.tvAltPhoneNumber.text.toString()
        )
    )


}