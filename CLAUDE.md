# CLAUDE.md — android-mvi Architecture Rules

Refer to `STRUCTURE.md` only when creating a feature folder or running a scaffold command.
Refer to `{FEATURE}.md` inside the feature folder only when working on that specific feature.

---

## Rules

- **UiEvent** — user actions only, no logic
- **UiState** — always a `data class`, immutable, update via `copy()`
- **UiEffect** — one-time events only (navigation, toast, snackbar)
- **ViewModel** — no Android framework imports except ViewModel & Hilt
- **Screen** — no business logic, only render state and send events
- **Component** — reusable Composables go in `presentation/component/`, never inside feature
- **UseCase** — one public `operator fun invoke()` per class
- **Repository** — interface in `domain/`, implementation in `data/`
- **Model** — DTO in `data/model/`, domain model in `domain/model/`, map explicitly

---

## Naming Convention

| Layer | Suffix | Example |
|---|---|---|
| Contract | `Contract` | `SampleContract` |
| ViewModel | `ViewModel` | `SampleViewModel` |
| Screen | `Screen` | `SampleScreen` |
| UseCase | `UseCase` | `GetSampleUseCase` |
| Repository Interface | `Repository` | `SampleRepository` |
| Repository Impl | `RepositoryImpl` | `SampleRepositoryImpl` |
| DTO | `Dto` | `SampleDto` |
| Component | descriptive | `SampleCard`, `LoadingIndicator` |

---

## When creating a new feature

- Always create `{FEATURE}.md` inside the feature folder
- Document the following:
    - Purpose of this feature
    - UiEvent / UiState / UiEffect overview
    - Related UseCases
    - Screen flow and navigation
    - Business logic or special rules
    - Any caveats or notes

## When working on an existing feature

- Always read `{FEATURE}.md` before making any changes
- Keep `{FEATURE}.md` up to date after any significant changes