package com.typingquest.app.domain

data class LevelConfig(
    val levelNumber: Int,
    val keysToUnlock: Set<Char>,
    val challengeText: String,
    val maxMistakes: Int,
    val targetAccuracy: Double
)

data class LevelResult(
    val levelNumber: Int,
    val wpm: Double,
    val accuracy: Double,
    val mistakes: Int,
    val completedAtMillis: Long
)
