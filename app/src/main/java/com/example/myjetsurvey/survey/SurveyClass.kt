package com.example.myjetsurvey.survey

data class Survey(
    val questionCount: Int,
    val questionList: List<Question>
)

data class SurveyResponse(
    val answerList: List<Response>
)

data class Question(
    val type: QuestionType,
    val title: String,
    val choiceCount: Int,
    val choiceList: List<String>
)

data class Response(
    val type: QuestionType,
    val responseList: List<String>,
    val response: String
)

sealed class MyResponse {
    data class SingleResponse(val index: Int): MyResponse()
    data class MultiResponse(val indexList: List<Int>): MyResponse()
    data class BlankResponse(val ans: String): MyResponse()
}


enum class QuestionType {
    MULTIPLE_CHOICE,
    SINGLE_CHOICE,
    FILL_IN_THE_BLANK,
}