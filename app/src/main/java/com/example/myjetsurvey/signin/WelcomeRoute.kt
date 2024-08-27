package com.example.myjetsurvey.signin

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WelcomeRoute(
    onNavigateToImportSurvey:()->Unit
){
    val welcomeViewModel: WelcomeViewModel = viewModel(factory = WelcomeViewModelFactory())
    WelcomeScreen(
        onNavigateToImportSurvey = {
            welcomeViewModel.signInAsGuest(onNavigateToImportSurvey)
        }
    )
}