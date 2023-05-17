package com.example.pawpal.screens.home.medicine

import com.example.pawpal.R

sealed class MedicinePage(val idTitle: Int) {
    object NotificationList : MedicinePage(R.string.notifications)
    object NotificationHistory : MedicinePage(R.string.history)
    object Notes : MedicinePage(R.string.notes)
}