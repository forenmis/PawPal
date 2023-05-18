package com.example.pawpal.screens.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.pawpal.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val callback: OnPageChangeCallback = object : OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bottomBar.selectedItemId = PageSettings.findPageByPosition(position).menuId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        val homeAdapter = HomeAdapter(
            items = listOf(
                PageSettings.Dashboard,
                PageSettings.Medicine,
                PageSettings.Contacts,
                PageSettings.Settings
            ),
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )
        viewModel.processExitLD.observe(viewLifecycleOwner) { isExit ->
            if (isExit) {
                viewModel.processExitLD.postValue(false)
                val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
        viewModel.idNotificationLD.observe(viewLifecycleOwner) { id ->
            if (id != null) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToNotificationDetailsFragment(id)
                viewModel.idNotificationLD.postValue(null)
                findNavController().navigate(action)
            }
        }
        viewModel.navigationLD.observe(viewLifecycleOwner) { isNavigate ->
            if (isNavigate) {
                viewModel.navigationLD.postValue(false)
                val action =
                    HomeFragmentDirections.actionHomeFragmentToCreateNotificationFragment()
                findNavController().navigate(action)
            }
        }
        binding.vpContainer.adapter = homeAdapter
        binding.vpContainer.isUserInputEnabled = false
        binding.bottomBar.setOnItemSelectedListener { item ->
            binding.vpContainer.currentItem = PageSettings.findPageByMenuId(item.itemId).position
            return@setOnItemSelectedListener true
        }
    }

    override fun onStart() {
        super.onStart()
        binding.vpContainer.registerOnPageChangeCallback(callback)
    }

    override fun onStop() {
        super.onStop()
        binding.vpContainer.unregisterOnPageChangeCallback(callback)
    }
}

