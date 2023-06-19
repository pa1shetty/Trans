package com.example.trans.screens.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.trans.R
import com.example.trans.databinding.HomeRootScreenBinding
import com.example.trans.utillity.UtilsClassUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeRootScreen : Fragment() {
    lateinit var homeScreenBinding: HomeRootScreenBinding
    private val viewModel by viewModels<HomeRootScreenViewModel>()

    @Inject
    lateinit var utilsClassUI: UtilsClassUI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeScreenBinding = HomeRootScreenBinding.inflate(inflater, container, false)
        return homeScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        homeScreenBinding.bottomNavigation.setupWithNavController(navController)
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.userName.collect { userName ->
                utilsClassUI.animateTextViewIn(
                    homeScreenBinding.tvUserName,
                    getString(R.string.home_screen_welcome, userName)
                )
            }
        }
    }


}