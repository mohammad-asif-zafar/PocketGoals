# PocketGoals - Development Rules & Guidelines

## 1. What to Upto (Best Practices)

- **KMP First**: Keep as much logic as possible in `commonMain`.
- **Material 3**: Use Material 3 components and design tokens consistently.
- **Type Safety**: Use Kotlinx Serialization for navigation and data models.
- **Responsiveness**: Ensure layouts work well on different screen sizes (phones/tablets).
- **Clean Code**: Follow naming conventions: `CamelCase` for classes, `camelCase` for
  variables/functions.

## 2. What to Avoid

- **Platform-Specific Bloat**: Avoid putting logic in `androidMain` or `iosMain` unless strictly
  necessary for platform APIs.
- **Hardcoded Strings**: Use the localization system for all user-facing text.
- **Direct State Mutation**: Always update state through ViewModels using `MutableStateFlow` or
  similar.
- **Implicit Receivers**: Avoid ambiguous scope calls in Compose (e.g., use explicit receivers for
  `AnimatedVisibility` when nested).

## 3. Libraries, Error Handling, Boundaries for AI

- **Standard Libraries**: Only use approved libraries in `libs.versions.toml`.
- **Error Handling**: Use `Result` or custom `Failure` objects for business logic errors. Show
  user-friendly error messages via `Snackbar` or `AlertDialog`.
- **AI Boundaries**:
    - AI should respect the existing Clean Architecture.
    - AI should not add new dependencies without checking the version catalog.
    - AI should prioritize readability and maintainability over clever "one-liners".
