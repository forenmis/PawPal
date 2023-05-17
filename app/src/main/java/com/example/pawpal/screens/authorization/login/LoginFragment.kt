package com.example.pawpal.screens.authorization.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.R
import com.example.pawpal.databinding.FragmentLoginBinding
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processLoginLD.observe(viewLifecycleOwner) { isProcess ->
            if (isProcess) {
                binding.btSignIn.text = getString(R.string.sending)
                downloadButton()
            } else {
                binding.btSignIn.text = getString(R.string.sign_in)
                binding.btSignIn.icon = null
            }
        }
        viewModel.errorLD.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.screenLD.observe(viewLifecycleOwner) { variant ->
            variant ?: return@observe
            val action = when (variant) {
                VariantScreen.Home -> {
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                }

                VariantScreen.Onboarding -> {
                    LoginFragmentDirections.actionLoginFragmentToOnboardingFragment()
                }
            }
            viewModel.screenLD.value = null
            findNavController().navigate(action)
        }
        binding.btSignIn.setOnClickListener {
            val username = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.length <= 2) {
                binding.etLogin.error = getString(R.string.so_small_text)
            } else if (password.length <= 2) {
                binding.etPassword.error = getString(R.string.so_small_text)
            } else {
                viewModel.login(username, password)
            }
        }
        binding.tvNoAcc.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }

    private fun downloadButton() {
        val spec =
            CircularProgressIndicatorSpec(
                requireContext(), null, 0,
                com.google.android.material.R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall
            )
        val progressIndicatorDrawable =
            IndeterminateDrawable.createCircularDrawable(requireContext(), spec)
        binding.btSignIn.icon = progressIndicatorDrawable
    }

}