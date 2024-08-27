package com.example.myjetsurvey

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myjetsurvey.ui.theme.MyJetsurveyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        appContext = applicationContext
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MyJetsurveyTheme {
                // A surface container using the 'background' color from the theme
                MyJetSurveyNavHost()
            }
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}
