package com.typingquest.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val allKeys = "abcdefghijklmnopqrstuvwxyz1234567890,./;".toSet()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeyboardProgress(unlockedKeys: Set<Char>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Keyboard Unlock Progress")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                allKeys.forEach { key ->
                    val unlocked = unlockedKeys.contains(key)
                    Text(
                        text = key.toString(),
                        color = if (unlocked) Color(0xFF27AE60) else Color(0xFF8E8E93)
                    )
                }
            }
            Text("Unlocked: ${unlockedKeys.size}/${allKeys.size}")
        }
    }
}
