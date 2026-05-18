package com.typingquest.app.ui

import androidx.lifecycle.ViewModel
import com.typingquest.app.data.LevelRepository
import com.typingquest.app.domain.LevelResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.max

class TypingQuestViewModel : ViewModel() {
    private val repository = LevelRepository()
    private val _uiState = MutableStateFlow(TypingUiState.initial(repository))
    val uiState = _uiState.asStateFlow()

    fun onTyped(input: String) {
        val state = _uiState.value
        if (state.isLevelCompleted || state.isGameCompleted) return

        val target = state.currentLevel.challengeText
        val expectedPrefix = target.take(input.length)

        val mistakes = countMistakes(input, expectedPrefix)
        val accuracy = if (input.isNotEmpty()) (input.length - mistakes).toDouble() / input.length else 1.0

        if (mistakes > state.currentLevel.maxMistakes) {
            _uiState.value = state.copy(
                typedText = input,
                mistakes = mistakes,
                isFailed = true,
                report = null,
                accuracy = accuracy
            )
            return
        }

        val completed = input == target
        if (completed) {
            val elapsedMs = max(System.currentTimeMillis() - state.levelStartTimeMillis, 1)
            val words = target.length / 5.0
            val minutes = elapsedMs / 60_000.0
            val wpm = words / minutes

            val report = LevelResult(
                levelNumber = state.currentLevel.levelNumber,
                wpm = wpm,
                accuracy = accuracy,
                mistakes = mistakes,
                completedAtMillis = System.currentTimeMillis()
            )

            val unlocked = state.unlockedKeys + state.currentLevel.keysToUnlock
            val gameDone = state.currentLevel.levelNumber == repository.maxLevel()

            _uiState.value = state.copy(
                typedText = input,
                mistakes = mistakes,
                accuracy = accuracy,
                isLevelCompleted = true,
                isGameCompleted = gameDone,
                unlockedKeys = unlocked,
                report = report,
                isFailed = accuracy < state.currentLevel.targetAccuracy
            )
        } else {
            _uiState.value = state.copy(
                typedText = input,
                mistakes = mistakes,
                accuracy = accuracy,
                isFailed = false,
                report = null
            )
        }
    }

    fun retryLevel() {
        val state = _uiState.value
        _uiState.value = state.copy(
            typedText = "",
            mistakes = 0,
            accuracy = 1.0,
            isFailed = false,
            isLevelCompleted = false,
            report = null,
            levelStartTimeMillis = System.currentTimeMillis()
        )
    }

    fun goToNextLevel() {
        val current = _uiState.value
        val nextLevel = current.currentLevel.levelNumber + 1
        val config = repository.getLevel(nextLevel) ?: return
        _uiState.value = current.copy(
            currentLevel = config,
            typedText = "",
            mistakes = 0,
            accuracy = 1.0,
            isFailed = false,
            isLevelCompleted = false,
            report = null,
            levelStartTimeMillis = System.currentTimeMillis()
        )
    }

    fun dismissReport() {
        _uiState.value = _uiState.value.copy(report = null)
    }

    private fun countMistakes(input: String, expected: String): Int {
        var wrong = 0
        input.forEachIndexed { index, c ->
            if (index >= expected.length || c != expected[index]) wrong++
        }
        return wrong
    }
}

data class TypingUiState(
    val currentLevel: com.typingquest.app.domain.LevelConfig,
    val typedText: String,
    val mistakes: Int,
    val accuracy: Double,
    val unlockedKeys: Set<Char>,
    val isFailed: Boolean,
    val isLevelCompleted: Boolean,
    val isGameCompleted: Boolean,
    val levelStartTimeMillis: Long,
    val report: LevelResult?
) {
    companion object {
        fun initial(repo: LevelRepository): TypingUiState {
            val first = repo.getLevel(1) ?: error("No levels configured")
            return TypingUiState(
                currentLevel = first,
                typedText = "",
                mistakes = 0,
                accuracy = 1.0,
                unlockedKeys = emptySet(),
                isFailed = false,
                isLevelCompleted = false,
                isGameCompleted = false,
                levelStartTimeMillis = System.currentTimeMillis(),
                report = null
            )
        }
    }
}
