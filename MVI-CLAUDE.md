# CLAUDE.md — android-mvi Architecture Guide

This file describes the architecture conventions of this project.
When generating any new feature or file, strictly follow the structure and patterns below.

---

## Project Layer Structure

```
app/src/main/java/com/us9988/mvi/
├── data/
│   ├── local/               # Room, DataStore implementations
│   ├── remote/              # Retrofit API interfaces & implementations
│   ├── model/               # DTOs (Data Transfer Objects)
│   └── repository/          # Repository implementations
├── domain/
│   ├── model/               # Domain models (pure Kotlin)
│   ├── repository/          # Repository interfaces
│   └── usecase/             # Use cases (one action per class)
└── presentation/
    ├── component/            # Reusable Composable UI components
    └── feature/
        └── {feature}/
            ├── {Feature}Contract.kt   # UiEvent + UiState + UiEffect
            ├── {Feature}ViewModel.kt  # ViewModel
            └── {Feature}Screen.kt    # Composable Screen
```

---

## Base Classes

Located at: `presentation/base/`

### MviEvent
```kotlin
interface MviEvent
```

### MviState
```kotlin
interface MviState
```

### MviEffect
```kotlin
interface MviEffect
```

### MviViewModel
```kotlin
abstract class MviViewModel<Event : MviEvent, State : MviState, Effect : MviEffect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    protected val currentState: State
        get() = _state.value

    fun handleEvent(event: Event) {
        onEvent(event)
    }

    protected abstract fun onEvent(event: Event)

    protected fun setState(reducer: State.() -> State) {
        _state.value = currentState.reducer()
    }

    protected suspend fun sendEffect(effect: Effect) {
        _effect.send(effect)
    }

    override fun onCleared() {
        super.onCleared()
        _effect.close()
    }
}
```

---

## Feature Convention

### 1. Contract (interface)
- File: `{Feature}Contract.kt`
- Always use `interface` — not `object` or `class`
- Contains: `UiEvent`, `UiState`, `UiEffect`

```kotlin
interface {Feature}Contract {

    sealed class UiEvent : MviEvent {
        // Define user actions here
    }

    data class UiState(
        val isLoading: Boolean = false,
        // Add UI state fields here
    ) : MviState

    sealed class UiEffect : MviEffect {
        // Define one-time effects here (navigation, toast, etc.)
    }
}
```

### 2. ViewModel
- File: `{Feature}ViewModel.kt`
- Extends `MviViewModel<Event, State, Effect>`
- Use `setState { }` to update state
- Use `sendEffect()` to emit one-time effects

```kotlin
@HiltViewModel
class {Feature}ViewModel @Inject constructor(
    // inject use cases here
) : MviViewModel<
    {Feature}Contract.UiEvent,
    {Feature}Contract.UiState,
    {Feature}Contract.UiEffect
>(
    {Feature}Contract.UiState()
) {
    override fun onEvent(event: {Feature}Contract.UiEvent) {
        when (event) {
            // handle events here
        }
    }
}
```

### 3. Screen
- File: `{Feature}Screen.kt`
- Collect `state` via `collectAsStateWithLifecycle()`
- Collect `effect` via `LaunchedEffect`

```kotlin
@Composable
fun {Feature}Screen(
    viewModel: {Feature}ViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                // handle one-time effects here
            }
        }
    }

    // Render UI using state
}
```

---

## Rules

- **UiEvent** — user actions only, no logic
- **UiState** — always a `data class`, immutable, use `copy()` to update
- **UiEffect** — one-time events only (navigation, toast, snackbar, etc.)
- **ViewModel** — no Android framework imports except ViewModel & Hilt
- **Screen** — no business logic, only render state and send events
- **Component** — reusable Composables go in `presentation/component/`, never inside feature
- **UseCase** — one public `operator fun invoke()` per class
- **Repository** — interface in `domain/`, implementation in `data/`
- **Model** — DTO in `data/model/`, domain model in `domain/model/`, map between them explicitly

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
