package com.example.myjetsurvey.survey

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ImportSurveyRoute(
    onNavigateToSurvey: (String) -> Unit,
    onNavUp: () -> Unit
) {
    val importSurveyViewModel: ImportSurveyViewModel =
        viewModel(factory = ImportSurveyViewModelFactory())
    ImportSurveyScreen(
        onConfirmPressed = {
            importSurveyViewModel.handleConfirm(
                importSurveyViewModel.jsonText,
                onNavigateToSurvey
            )
        },
        onValueChange = importSurveyViewModel::onValueChange,
        currentJsonText = importSurveyViewModel.jsonText,
        onNavUp = onNavUp
    )
}