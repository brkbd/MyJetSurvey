package com.example.myjetsurvey.survey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SurveyViewModel(
    private val survey: Survey
) : ViewModel() {
    private var questionIndex = 0

    private val questionOrder = survey.questionList
    private val _responseOrder = mutableStateListOf(
        Response(
            type = questionOrder[0].type,
            responseList = mutableStateListOf(),
            response = ""
        )
    )
    val responseOrder: List<Response>
        get() = _responseOrder

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    var myNext: Boolean by mutableStateOf(false)
        private set

    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        if (_responseOrder.size <= questionIndex + 1) {
            _responseOrder.add(
                Response(
                    type = questionOrder[questionIndex + 1].type,
                    responseList = mutableListOf(),
                    response = ""
                )
            )
        }
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(
        onSurveyComplete: () -> Unit,
        onSavingResponse: (List<Response>) -> Unit,
        onSavingSurvey: (Survey) -> Unit
    ) {
        onSavingResponse(responseOrder)
        onSavingSurvey(survey)
        onSurveyComplete()
    }

    fun onMultipleChoiceResponse(selected: Boolean, answer: String) {
        val responseListTemp = _responseOrder[questionIndex].responseList.toMutableList()
        if (selected) {
            responseListTemp.add(answer)
            _responseOrder[questionIndex] =
                _responseOrder[questionIndex].copy(
                    responseList = responseListTemp,
                )
        } else {
            responseListTemp.remove(answer)
            _responseOrder[questionIndex] =
                _responseOrder[questionIndex].copy(
                    responseList = responseListTemp,
                )
        }
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSingleChoiceResponse(answer: String) {
        _responseOrder[questionIndex] = _responseOrder[questionIndex].copy(response = answer)
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onFillInTheBlankResponse(answer: String) {
        _responseOrder[questionIndex] = _responseOrder[questionIndex].copy(response = answer)
        _isNextEnabled.value = getIsNextEnabled()
    }

    private fun getIsNextEnabled(): Boolean {
        val response = responseOrder[questionIndex]
        return if (response.type != QuestionType.MULTIPLE_CHOICE) {
            response.response != ""
        } else {
            response.responseList.isNotEmpty()
        }
    }

    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex]
        )
    }
}

class SurveyViewModelFactory(private val survey: Survey) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(survey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: Question
)