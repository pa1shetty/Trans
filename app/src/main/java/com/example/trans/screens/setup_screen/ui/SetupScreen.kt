package com.example.trans.screens.setup_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.trans.databinding.SetupScreenBinding
import com.example.trans.network.enums.NavigateTo
import com.example.trans.screens.setup_screen.utils.states.ConfigSetupState
import com.example.trans.screens.setup_screen.vm.SetupScreenVM
import com.example.trans.utillity.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class SetupScreen : Fragment() {
    private var _binding: SetupScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SetupScreenVM>()
    private lateinit var splashScreen: SplashScreen


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = requireActivity().installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
    }

    private fun observers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.configSetUpState.collect { uiState ->
                when (uiState) {
                    ConfigSetupState.DONE -> {
                        navigateTo()
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun navigateTo() {
        splashScreen.setKeepOnScreenCondition { true }
        viewModel.navigateTo { navigateTo ->
            lifecycleScope.launch(Dispatchers.IO) {
                when (navigateTo) {
                    NavigateTo.LOGIN_SCREEN -> navigateToBg(SetupScreenDirections.actionSetupScreenToLoginScreen())
                    NavigateTo.HOME_SCREEN -> navigateToBg(SetupScreenDirections.actionSetupScreenToHomeScreen())
                    NavigateTo.UPDATE_SCREEN -> navigateToBg(SetupScreenDirections.actionSetupScreenToAppUpdateScreen())
                    NavigateTo.REG_SCREEN -> navigateToBg(SetupScreenDirections.actionSetupScreenToRegistrationFragment())
                }
            }
        }
    }

    private suspend fun navigateToBg(navDirections: NavDirections) {
        withContext(Dispatchers.Main) {
            navigateTo(navDirections)
            splashScreen.setKeepOnScreenCondition { false }
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                findNavController().navigate(navDirections)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SetupScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateTo()
        //skip config
        //observers()
    }
}