package com.example.pawpal.screens.home.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pawpal.databinding.FragmentMedicineBinding
import com.google.android.material.tabs.TabLayoutMediator

class MedicineFragment : Fragment() {

    private val viewModel by viewModels<MedicineViewModel>()
    private lateinit var binding: FragmentMedicineBinding

    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterMedicine(
            items = listOf(
                MedicinePage.NotificationList,
                MedicinePage.NotificationHistory,
                MedicinePage.Notes
            ),
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )
        binding.vpContainerMedicine.adapter = adapter
        tabLayoutMediator =
            TabLayoutMediator(binding.tabMedicine, binding.vpContainerMedicine) { tab, position ->
                tab.text = getString(adapter.getItem(position).idTitle)
            }
    }

    override fun onResume() {
        super.onResume()
        tabLayoutMediator?.attach()
    }

    override fun onPause() {
        super.onPause()
        tabLayoutMediator?.detach()
    }

    override fun onDestroyView() {
        tabLayoutMediator = null
        super.onDestroyView()
    }
}