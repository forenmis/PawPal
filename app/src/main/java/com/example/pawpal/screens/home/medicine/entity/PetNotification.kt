package com.example.pawpal.screens.home.medicine.entity

import java.util.Date

data class PetNotification(
    val id: Long = 0,
    val title: String,
    val date: Date,
    val isPeriod: Boolean,
    val periodDays: Int?,
    val remindIn: Int
)
