package com.example.pawpal.screens.authorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pawpal.R
import com.example.pawpal.databinding.FragmentRegistrationBinding
import com.example.pawpal.screens.authorization.InputError
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processRegistrationLD.observe(viewLifecycleOwner) {
            if (it) {
                binding.btSignUp.text = getString(R.string.sending)
                downloadButton()
            } else {
                binding.btSignUp.text = getString(R.string.create)
                binding.btSignUp.icon = null
            }
        }
        viewModel.errorLD.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_SHORT
                ).show()
                binding.btSignUp.icon = null
            }
        }
        viewModel.inputErrorsLD.observe(viewLifecycleOwner) { errors ->
            with(binding) {
                errors.forEach { error ->
                    when (error) {
                        is InputError.EmailError -> etEmail.error =
                            getString(R.string.invalid_email)

                        is InputError.PasswordError -> etPassword.error =
                            getString(R.string.so_small_text)

                        is InputError.PasswordsNotEquals -> etCheckPass.error =
                            getString(R.string.repeat_pass)

                        is InputError.UserNameError -> etLogin.error =
                            getString(R.string.so_small_text)
                    }
                }
            }
        }
        viewModel.registeredLD.observe(viewLifecycleOwner) { isRegistered ->
            if (isRegistered) {
                viewModel.registeredLD.value = false
                val action =
                    RegistrationFragmentDirections.actionRegistrationFragmentToOnboardingFragment()
                findNavController().navigate(action)
            }
        }
        binding.btSignUp.setOnClickListener {
            with(binding) {
                viewModel.registration(
                    password = etPassword.text.toString(),
                    rePassword = etCheckPass.text.toString(),
                    username = etLogin.text.toString(),
                    email = etEmail.text.toString()
                )
            }
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
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
        binding.btSignUp.icon = progressIndicatorDrawable
    }
}
