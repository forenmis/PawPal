package com.example.pawpal.screens.authorization

sealed class InputError {
    object EmailError : InputError()
    object PasswordsNotEquals : InputError()
    object UserNameError : InputError()
    object PasswordError : InputError()
}