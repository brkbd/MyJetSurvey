package com.example.myjetsurvey.survey.question

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myjetsurvey.R

@Composable
fun FillInTheBlankQuestion(
    title: String,
    onValueChange: (String) -> Unit,
    currentAnswer: String,
    modifier: Modifier = Modifier
) {
    QuestionWrapper(
        modifier = modifier,
        title = title,
        directions = stringResource(id = R.string.direction_for_fill_in_the_blank),
    ) {
        AnswerForText(
            onValueChange = onValueChange,
            currentAnswer = currentAnswer
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerForText(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    currentAnswer: String
) {
    OutlinedTextField(
        value = currentAnswer,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(
                text = stringResource(id = R.string.answer),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        singleLine = true
    )
}


@Preview
@Composable
fun FillInTheBlankQuestionPreview() {
    FillInTheBlankQuestion(
        title = "What genre of movies do you like to watch?",
        onValueChange = { _ -> },
        currentAnswer = ""
    )
}

