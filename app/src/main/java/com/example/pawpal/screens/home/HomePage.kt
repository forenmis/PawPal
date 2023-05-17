package com.example.pawpal.screens.home

import com.example.pawpal.R

sealed class PageSettings(val position: Int, val menuId: Int) {
    object Dashboard : PageSettings(0, R.id.menuFragmentDashboard)
    object Medicine : PageSettings(1, R.id.menuFragmentMedicine)
    object Contacts : PageSettings(2, R.id.menuFragmentContacts)
    object Settings : PageSettings(3, R.id.menuFragmentSettings)

    companion object {
        fun findPageByPosition(position: Int): PageSettings {
            return when (position) {
                Dashboard.position -> Dashboard
                Medicine.position -> Medicine
                Contacts.position -> Contacts
                Settings.position -> Settings
                else -> throw IllegalArgumentException()
            }
        }

        fun findPageByMenuId(menuId: Int): PageSettings {
            return when (menuId) {
                Dashboard.menuId -> Dashboard
                Medicine.menuId -> Medicine
                Contacts.menuId -> Contacts
                Settings.menuId -> Settings
                else -> throw IllegalArgumentException()
            }
        }
    }
}