package com.example.pawpal.entity

sealed class Item {
    data class FoodItem(val food: Food) : Item()
    data class ToyItem(val toy: Toy) : Item()
    object SeeAll : Item()
}