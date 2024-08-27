package com.example.myjetsurvey.util

import com.example.myjetsurvey.survey.Survey
import com.example.myjetsurvey.survey.SurveyResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@OptIn(ExperimentalStdlibApi::class)
fun jsonToSurvey(json: String): Survey? {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter:JsonAdapter<Survey> =moshi.adapter()
    return jsonAdapter.fromJson(json)
}

@OptIn(ExperimentalStdlibApi::class)
fun surveyToJson(survey: Survey): String {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter:JsonAdapter<Survey> =moshi.adapter()
    return jsonAdapter.toJson(survey)
}

@OptIn(ExperimentalStdlibApi::class)
fun surveyResponseToJson(surveyResponse: SurveyResponse): String {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter:JsonAdapter<SurveyResponse> =moshi.adapter()
    return jsonAdapter.toJson(surveyResponse)
}

