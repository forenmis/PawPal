package com.example.pawpal.screens.splash_screen

sealed interface Action {
    object ToLogin : Action
    object ToHome : Action
}