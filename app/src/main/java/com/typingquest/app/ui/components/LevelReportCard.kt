package com.typingquest.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.typingquest.app.domain.LevelResult

@Composable
fun LevelReportCard(report: LevelResult, onDismiss: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Level ${report.levelNumber} Complete")
            Text("WPM: ${"%.1f".format(report.wpm)}")
            Text("Accuracy: ${(report.accuracy * 100).toInt()}%")
            Text("Mistakes: ${report.mistakes}")
            Button(onClick = onDismiss) { Text("Close Report") }
        }
    }
}
