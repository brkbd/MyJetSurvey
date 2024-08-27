package com.example.myjetsurvey.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WelcomeViewModel() : ViewModel() {
    fun signInAsGuest(
        onSignInComplete: () -> Unit
    ) {
        onSignInComplete()
    }
}

class WelcomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}