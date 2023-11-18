package com.techullurgy.tuitiontracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.remember
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.unit.sp
import com.techullurgy.tuitiontracker.android.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                val currentTextStyle = LocalTextStyle.current

                val poppinsTextStyle = remember(currentTextStyle) {
                    currentTextStyle.copy(
                        fontFamily = PoppinsFontFamily,
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                        letterSpacing = 0.sp
                    )
                }

                ProvideTextStyle(value = poppinsTextStyle) {
                    MainScreen()
                }
            }
        }
    }
}