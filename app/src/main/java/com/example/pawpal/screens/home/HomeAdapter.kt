package com.example.pawpal.screens.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pawpal.screens.home.contacts.ContactsFragment
import com.example.pawpal.screens.home.dashboard.DashboardFragment
import com.example.pawpal.screens.home.medicine.MedicineFragment
import com.example.pawpal.screens.home.settings.SettingsFragment

class HomeAdapter(
    private val items: List<PageSettings>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return when (items[position]) {
            PageSettings.Contacts -> ContactsFragment()
            PageSettings.Medicine -> MedicineFragment()
            PageSettings.Dashboard -> DashboardFragment()
            PageSettings.Settings -> SettingsFragment()
        }
    }
}