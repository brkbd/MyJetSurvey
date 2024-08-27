package com.example.myjetsurvey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myjetsurvey.Destinations.IMPORT_SURVEY_ROUTE
import com.example.myjetsurvey.Destinations.SURVEY_RESULTS_ROUTE
import com.example.myjetsurvey.Destinations.SURVEY_ROUTE
import com.example.myjetsurvey.Destinations.WELCOME_ROUTE
import com.example.myjetsurvey.signin.WelcomeRoute
import com.example.myjetsurvey.survey.ImportSurveyRoute
import com.example.myjetsurvey.survey.Survey
import com.example.myjetsurvey.survey.SurveyResultScreen
import com.example.myjetsurvey.survey.SurveyRoute
import com.example.myjetsurvey.util.jsonToSurvey

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val IMPORT_SURVEY_ROUTE = "importsurvey"
    const val SURVEY_ROUTE = "survey/{survey}"
    const val SURVEY_RESULTS_ROUTE = "surveyresults"
}

@Composable
fun MyJetSurveyNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = WELCOME_ROUTE
    ) {
        composable(WELCOME_ROUTE) {
            WelcomeRoute(
                onNavigateToImportSurvey = {
                    navController.navigate(IMPORT_SURVEY_ROUTE)
                }
            )
        }
        composable(IMPORT_SURVEY_ROUTE) {
            ImportSurveyRoute(
                onNavigateToSurvey = {
                    navController.navigate("survey/$it")
                },
                onNavUp = navController::navigateUp
            )
        }
        composable(SURVEY_ROUTE) {
            val jsonSurvey = it.arguments?.getString("survey")
            val survey: Survey?
            if (jsonSurvey == null) {
                throw IllegalArgumentException("The json text is null")
            } else {
                survey = jsonToSurvey(jsonSurvey)
            }
            if (survey != null) {
                SurveyRoute(
                    onSurveyComplete = {
                        navController.navigate(SURVEY_RESULTS_ROUTE)
                    },
                    onNavUp = navController::navigateUp,
                    survey = survey
                )
            }
        }
        composable(SURVEY_RESULTS_ROUTE) {
            SurveyResultScreen {
                navController.popBackStack(WELCOME_ROUTE, false)
            }
        }
    }
}




