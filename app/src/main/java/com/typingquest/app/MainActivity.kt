package com.typingquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.typingquest.app.ui.TypingQuestApp
import com.typingquest.app.ui.TypingQuestViewModel
import com.typingquest.app.ui.theme.TypingQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TypingQuestViewModel = viewModel()
            val state by viewModel.uiState.collectAsState()

            TypingQuestTheme {
                TypingQuestApp(
                    state = state,
                    onTyped = viewModel::onTyped,
                    onNextLevel = viewModel::goToNextLevel,
                    onRetry = viewModel::retryLevel,
                    onDismissReport = viewModel::dismissReport
                )
            }
        }
    }
}
