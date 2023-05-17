package com.example.pawpal.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pawpal.databinding.FragmentOnboardingBinding
import com.google.android.material.R
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.snackbar.Snackbar

class OnboardingFragment : Fragment() {

    private val viewModel by viewModels<OnboardingViewModel>()

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            processLD.observe(viewLifecycleOwner) { isProcess ->
                if (isProcess) {
                    binding.btAdd.text = getString(com.example.pawpal.R.string.sending)
                    downloadButton()
                } else {
                    binding.btAdd.text = getString(com.example.pawpal.R.string.add)
                    binding.btAdd.icon = null
                }
            }
            errorLD.observe(viewLifecycleOwner) { isError ->
                if (isError) {
                    Snackbar.make(
                        binding.root,
                        getString(com.example.pawpal.R.string.no_internet_connection),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            isAddedLD.observe(viewLifecycleOwner) { isAdded ->
                if (isAdded) {
                    Snackbar.make(
                        binding.root,
                        getString(com.example.pawpal.R.string.ok),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            inputErrorsLD.observe(viewLifecycleOwner) { errors ->
                with(binding) {
                    errors.forEach { error ->
                        when (error) {
                            FieldsError.AgeError -> etPetAge.error =
                                (getString(com.example.pawpal.R.string.age_error))

                            FieldsError.NameError -> etPetName.error =
                                getString(com.example.pawpal.R.string.so_small_text)
                        }
                    }
                }
            }
        }
        with(binding) {
            btAdd.setOnClickListener {
                viewModel.addPet(
                    name = etPetName.text.toString(),
                    age = etPetAge.text.toString().toInt()
                )
            }
        }
    }

    private fun downloadButton() {
        val spec =
            CircularProgressIndicatorSpec(
                requireContext(), null, 0,
                R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall
            )
        val progressIndicatorDrawable =
            IndeterminateDrawable.createCircularDrawable(requireContext(), spec)
        binding.btAdd.icon = progressIndicatorDrawable
    }
}

