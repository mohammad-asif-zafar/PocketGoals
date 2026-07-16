# PocketGoals - Architecture & Tech Stack

## 1. Top Flow and Architecture

The project follows **Clean Architecture** principles combined with **MVI (Model-View-Intent)** or *
*MVVM (Model-View-ViewModel)** patterns, optimized for Kotlin Multiplatform.

- **Presentation Layer**: Compose Multiplatform for UI, using ViewModels to handle state.
- **Domain Layer**: Contains business logic, use cases (if applicable), and domain models.
- **Data Layer**: Responsible for data fetching from local (Multiplatform Settings) or remote
  sources.

## 2. Folder and File Structure

- `androidApp/`: Android-specific entry point and resources.
- `iosApp/`: iOS-specific entry point (Swift/Xcode project).
- `shared/src/commonMain/kotlin/com/hathway/pocketgoals/`:
    - `presentation/`:
        - `ui/`: Screens, components, theme, navigation.
        - `viewmodel/`: State management for each screen.
    - `domain/`: Business models and logic.
    - `data/`: Repositories and local/remote data sources.
- `shared/src/androidMain/`: Android-specific implementations.
- `shared/src/iosMain/`: iOS-specific implementations.

## 3. Tech and Stack

- **Language**: Kotlin 2.0+
- **UI Framework**: Compose Multiplatform
- **Navigation**: Jetpack Navigation (Compose)
- **State Management**: Lifecycle ViewModel, StateFlow
- **Serialization**: Kotlinx Serialization
- **Local Storage**: Multiplatform Settings
- **Date/Time**: Kotlinx Datetime
- **Build System**: Gradle with Version Catalogs




-------



