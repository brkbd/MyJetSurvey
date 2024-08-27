package com.example.myjetsurvey.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myjetsurvey.R
import com.example.myjetsurvey.ui.theme.MyJetsurveyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportSurveyScreen(
    onConfirmPressed: () -> Unit,
    onValueChange: (String) -> Unit,
    currentJsonText: String,
    onNavUp: () -> Unit
) {
    Scaffold(
        topBar = {
            ImportSurveyTopAppBar(topAppBarText = "Import Survey",
                onNavUp=onNavUp)
        },
        content = {contentPadding->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth()
            ) {
                ClipBoardForJsonText(onValueChange = onValueChange,
                    currentJsonText = currentJsonText)
                Spacer(modifier = Modifier.padding(10.dp))
                Confirmed(onConfirmPressed = onConfirmPressed)
            }
        }
    ) 
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportSurveyTopAppBar(
    topAppBarText: String,
    onNavUp: () -> Unit
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = topAppBarText,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
    },
        navigationIcon = {
            IconButton(onClick = onNavUp) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClipBoardForJsonText(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    currentJsonText: String
) {
    Column(
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(id = R.string.import_your_survey),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = currentJsonText,
            onValueChange = onValueChange,
            modifier = Modifier
                .height(500.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            label = {
                Text(
                    text = stringResource(id = R.string.json_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
}

@Composable
fun Confirmed(
    onConfirmPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(
            onClick = onConfirmPressed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 24.dp)
        ) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}

@Preview
@Composable
fun ImportSurveyScreenPreview() {
    MyJetsurveyTheme {
        Surface {
            ImportSurveyScreen(
                onConfirmPressed = {},
                onValueChange = {},
                currentJsonText = "",
                onNavUp = {}
            )
        }
    }
}