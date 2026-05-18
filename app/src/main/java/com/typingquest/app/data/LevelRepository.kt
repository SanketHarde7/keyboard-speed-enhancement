package com.typingquest.app.data

import com.typingquest.app.domain.LevelConfig

class LevelRepository {
    private val levelConfigs = listOf(
        LevelConfig(1, setOf('a', 's', 'd', 'f', 'j', 'k', 'l', ';'), "asdf jkl; asdf jkl;", 5, 0.85),
        LevelConfig(2, setOf('q', 'w', 'e', 'r', 'u', 'i', 'o', 'p'), "qwer uiop qwer uiop", 5, 0.86),
        LevelConfig(3, setOf('z', 'x', 'c', 'v', 'm', ',', '.', '/'), "zxcv m,./ zxcv m,./", 5, 0.87),
        LevelConfig(4, setOf('t', 'y', 'g', 'h', 'b', 'n'), "the big night begins", 4, 0.88),
        LevelConfig(5, setOf('1', '2', '3', '4', '5'), "12345 54321 12345", 4, 0.90),
        LevelConfig(6, setOf('6', '7', '8', '9', '0'), "67890 09876 67890", 4, 0.91)
    )

    fun getLevel(level: Int): LevelConfig? = levelConfigs.getOrNull(level - 1)

    fun maxLevel(): Int = levelConfigs.size
}
