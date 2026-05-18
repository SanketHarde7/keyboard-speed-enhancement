# Typing Quest (Android)

A game-like typing speed enhancement app where users clear levels to unlock keyboard keys and eventually unlock the full keyboard.

## Core Gameplay
- Progressive levels with curated key sets.
- Mistake pressure (max mistakes per level).
- Performance report after each completed level, including WPM.
- Final goal: unlock all keys.

## Tech
- Kotlin + Jetpack Compose
- MVVM-style state handling
- Clean separation between `data`, `domain`, and `ui`

## Future-ready for Premium
Current build is free-only, but architecture is ready for future premium features:
- Level repository can be split into free vs premium packs.
- UI state supports progression gates.
- Release build is configured with ProGuard for production.

## Build APK
1. Install Android Studio (latest stable with Android SDK 34).
2. Open this project.
3. Run:
   - Debug APK: `./gradlew assembleDebug`
   - Release APK: `./gradlew assembleRelease`
4. APK output will be under `app/build/outputs/apk/`.
