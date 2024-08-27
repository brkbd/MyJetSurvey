package com.example.myjetsurvey.survey

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myjetsurvey.MainActivity
import com.example.myjetsurvey.survey.question.FillInTheBlankQuestion
import com.example.myjetsurvey.survey.question.MultipleChoiceQuestion
import com.example.myjetsurvey.survey.question.SingleChoiceQuestion
import com.example.myjetsurvey.util.surveyResponseToJson
import com.example.myjetsurvey.util.surveyToJson
import java.io.File

private const val CONTENT_ANIMATION_DURATION = 300

@Composable
fun SurveyRoute(
    onSurveyComplete: () -> Unit,
    onNavUp: () -> Unit,
    survey: Survey
) {
    val viewModel: SurveyViewModel = viewModel(factory = SurveyViewModelFactory(survey))
    val surveyScreenData = viewModel.surveyScreenData ?: return

    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp()
        }
    }
    SurveyQuestionsScreen(
        surveyScreenData = surveyScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = { onNavUp() },
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = {
            val path = MainActivity.appContext.filesDir
            viewModel.onDonePressed(
                onSurveyComplete,
                onSavingResponse = {
                    val response = surveyResponseToJson(SurveyResponse(it))
                    val file = File(path, "response.json")
                    file.appendText(response)
                },
                onSavingSurvey = {
                    val file = File(path, "survey.json")
                    file.appendText(surveyToJson(it))
                }
            )
        }) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        AnimatedContent(
            targetState = surveyScreenData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)
                val direction = getTransitionDirection(
                    initialIndex = initialState.questionIndex,
                    targetIndex = targetState.questionIndex
                )
                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            },
            label = "surveyScreenDataAnimation"
        ) { targetState ->
            when (targetState.surveyQuestion.type) {
                QuestionType.FILL_IN_THE_BLANK -> {
                    FillInTheBlankQuestion(
                        title = targetState.surveyQuestion.title,
                        onValueChange = viewModel::onFillInTheBlankResponse,
                        currentAnswer = viewModel.responseOrder[targetState.questionIndex].response,
                        modifier = modifier
                    )
                }

                QuestionType.MULTIPLE_CHOICE -> {
                    MultipleChoiceQuestion(
                        title = targetState.surveyQuestion.title,
                        possibleAnswers = targetState.surveyQuestion.choiceList,
                        selectedAnswers = viewModel.responseOrder[targetState.questionIndex].responseList,
                        onOptionSelected = viewModel::onMultipleChoiceResponse,
                        modifier = modifier
                    )
                }

                QuestionType.SINGLE_CHOICE -> {
                    SingleChoiceQuestion(
                        title = targetState.surveyQuestion.title,
                        possibleAnswers = targetState.surveyQuestion.choiceList,
                        selectedAnswer = viewModel.responseOrder[targetState.questionIndex].response,
                        onOptionSelected = viewModel::onSingleChoiceResponse,
                        modifier = modifier
                    )
                }
            }
        }
    }
}


private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}
