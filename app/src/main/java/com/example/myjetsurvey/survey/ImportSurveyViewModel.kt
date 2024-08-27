package com.example.myjetsurvey.survey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImportSurveyViewModel() : ViewModel() {
    private var _jsonText by mutableStateOf("")
    val jsonText: String
        get() = _jsonText

    fun onValueChange(inputJsonText: String) {
        _jsonText = inputJsonText
    }

    fun handleConfirm(
        survey: String,
        onNavigateToSurvey: (String) -> Unit
    ) {
        onNavigateToSurvey(survey)
    }

}


class ImportSurveyViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImportSurveyViewModel::class.java)) {
            return ImportSurveyViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}