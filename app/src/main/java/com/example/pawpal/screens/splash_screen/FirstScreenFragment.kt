package com.example.pawpal.screens.splash_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class FirstScreenFragment : Fragment() {
    private val viewModel by viewModels<FirstScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkScreen()
        viewModel.actionLD.observe(this) { action ->
            val actionNav = when (action) {
                Action.ToHome -> FirstScreenFragmentDirections.actionFirstScreenFragmentToHomeFragment()
                Action.ToLogin -> FirstScreenFragmentDirections.actionFirstScreenFragmentToLoginFragment()
            }
            findNavController().navigate(actionNav)
        }
    }
}