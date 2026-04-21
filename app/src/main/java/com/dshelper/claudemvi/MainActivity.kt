package com.dshelper.claudemvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dshelper.claudemvi.presentation.feature.sample.SampleScreen
import com.dshelper.claudemvi.ui.theme.ClaudeMVITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClaudeMVITheme {
                SampleScreen()
            }
        }
    }
}
