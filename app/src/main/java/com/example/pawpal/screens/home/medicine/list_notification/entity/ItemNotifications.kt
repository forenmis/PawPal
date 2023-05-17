package com.example.pawpal.screens.home.medicine.list_notification.entity

import com.example.pawpal.screens.home.medicine.entity.PetNotification

sealed class ItemNotifications {
    data class NotificationItem(val petNotification: PetNotification) : ItemNotifications()
    data class GroupItem(val title: String) : ItemNotifications()
}
