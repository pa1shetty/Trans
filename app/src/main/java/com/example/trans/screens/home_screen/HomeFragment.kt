package com.example.trans.screens.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trans.R
import com.example.trans.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}