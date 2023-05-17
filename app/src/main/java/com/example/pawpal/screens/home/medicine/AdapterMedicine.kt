package com.example.pawpal.screens.home.medicine

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pawpal.screens.home.medicine.history_notification.HistoryNotificationFragment
import com.example.pawpal.screens.home.medicine.list_notification.NotificationListFragment
import com.example.pawpal.screens.home.medicine.notes.NotesFragment

class AdapterMedicine(
    private val items: List<MedicinePage>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return when (items[position]) {
            MedicinePage.Notes -> NotesFragment()
            MedicinePage.NotificationHistory -> HistoryNotificationFragment()
            MedicinePage.NotificationList -> NotificationListFragment()
        }
    }

    fun getItem(position: Int): MedicinePage = items[position]

}