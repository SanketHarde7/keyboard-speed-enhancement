package com.typingquest.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.typingquest.app.ui.components.KeyboardProgress
import com.typingquest.app.ui.components.LevelReportCard

@Composable
fun TypingQuestApp(
    state: TypingUiState,
    onTyped: (String) -> Unit,
    onNextLevel: () -> Unit,
    onRetry: () -> Unit,
    onDismissReport: () -> Unit
) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF12131A))
            .padding(16.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Typing Quest",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Level ${state.currentLevel.levelNumber}")
                Text("Target Accuracy: ${(state.currentLevel.targetAccuracy * 100).toInt()}%")
                Text("Mistakes: ${state.mistakes}/${state.currentLevel.maxMistakes}")
                Text("Accuracy: ${(state.accuracy * 100).toInt()}%")
                Text("Type this:")
                Text(state.currentLevel.challengeText, color = Color(0xFF34495E))
            }
        }

        OutlinedTextField(
            value = state.typedText,
            onValueChange = onTyped,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Type here") },
            enabled = !state.isLevelCompleted,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None)
        )

        if (state.isFailed && !state.isLevelCompleted) {
            Text(
                text = "Too many mistakes! Retry this level.",
                color = Color(0xFFE74C3C)
            )
            Button(onClick = onRetry) { Text("Retry") }
        }

        KeyboardProgress(state.unlockedKeys)

        state.report?.let {
            LevelReportCard(report = it, onDismiss = onDismissReport)
        }

        if (state.isLevelCompleted) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onRetry) { Text("Replay") }
                if (!state.isGameCompleted && !state.isFailed) {
                    Button(onClick = onNextLevel) { Text("Next Level") }
                }
            }
        }

        if (state.isGameCompleted) {
            Text("🏆 Full keyboard unlocked!", color = Color(0xFF2ECC71))
        }
    }
}
