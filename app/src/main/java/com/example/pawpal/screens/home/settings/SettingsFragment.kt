package com.example.pawpal.screens.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.pawpal.R
import com.example.pawpal.databinding.FragmentSettingsBinding
import com.example.pawpal.screens.home.HomeViewModel
import com.example.pawpal.utils.setImage
import com.example.pawpal.utils.setImageAvatar

class SettingsFragment : Fragment() {
    private val viewModelHome by activityViewModels<HomeViewModel>()
    private val viewModel by viewModels<SettingsViewModel>()

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { viewModel.uploadAvatar(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ivExit.setOnClickListener { viewModelHome.exit() }
        }
        viewModel.profileDataLD.observe(viewLifecycleOwner) { profile ->
            with(binding) {
                etEmail.setText(profile.user.email)
                etLogin.setText(profile.user.username)
                etPetAge.setText(profile.pet.age.toString())
                etPetName.setText(profile.pet.name)
            }
            checkAvatarProfile(profile)
        }
        viewModel.processLD.observe(viewLifecycleOwner) { inProgress ->
            binding.progressGroup.isVisible = inProgress
            binding.ivAvatarAction.isVisible = !inProgress
        }

    }

    private fun checkAvatarProfile(profile: ProfileData) {
        with(binding) {
            ivAvatar.setImageAvatar(profile.user.avatar)
            if (profile.user.avatar == null) {
                tvNameAvatar.isVisible = true
                tvNameAvatar.text = profile.user.username[0].toString()
                ivAvatarAction.setImage(R.drawable.ic_create_avatar)
                ivAvatarAction.setOnClickListener {
                    pickImageLauncher.launch("image/*")
                }
            } else {
                tvNameAvatar.isVisible = false
                ivAvatarAction.setImage(R.drawable.ic_delete)
                ivAvatarAction.setOnClickListener {
                    viewModel.deleteUserAvatar()
                }
            }
        }
    }
}