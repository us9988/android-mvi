# android-mvi

A lightweight MVI architecture for solo and small-team Android development, designed to work closely with Claude.

This project focuses on keeping the structure simple and readable, using Claude as a development partner to scaffold, implement, and maintain features consistently.

Any feedback or suggestions are always appreciated.

## Using with Claude

Three markdown files define how Claude understands and works within this project.

| File | When to use |
|---|---|
| `CLAUDE.md` | Always — core rules and conventions |
| `STRUCTURE.md` | When scaffolding or modifying project structure |
| `{FEATURE}.md` | When working on a specific feature |

### Scaffold entire project structure
```bash
read scaffold the entire project structure with base classes and a Sample feature
```

## Project Structure

```
app/src/main/java/com/us9988/mvi/
├── data/
│   ├── local/
│   ├── remote/
│   ├── model/
│   └── repository/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
└── presentation/
    ├── component/
    └── feature/
        └── {feature}/
            ├── {FEATURE}.md
            ├── {Feature}Contract.kt
            ├── {Feature}ViewModel.kt
            └── {Feature}Screen.kt
```

## Tech Stack

- **Language** — Kotlin
- **UI** — Jetpack Compose
- **Architecture** — MVI + 3-Layer (Data / Domain / Presentation)
- **Async** — Coroutines + Flow
- **DI** — Hilt
- **Network** — Retrofit + OkHttp
- **Local DB** — Room

## Usage

### 1. Define Contract
```kotlin
interface SampleContract {
    sealed class UiEvent : MviEvent {
        object LoadData : UiEvent()
        data class OnItemClick(val id: String) : UiEvent()
    }

    data class UiState(
        val isLoading: Boolean = false,
        val items: List<String> = emptyList(),
        val error: String? = null
    ) : MviState

    sealed class UiEffect : MviEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToDetail : UiEffect()
    }
}
```

### 2. ViewModel
```kotlin
@HiltViewModel
class SampleViewModel @Inject constructor() : MviViewModel<
    SampleContract.UiEvent,
    SampleContract.UiState,
    SampleContract.UiEffect
>(SampleContract.UiState()) {

    override fun onEvent(event: SampleContract.UiEvent) {
        when (event) {
            is SampleContract.UiEvent.LoadData -> loadData()
            is SampleContract.UiEvent.OnItemClick -> onItemClick(event.id)
        }
    }

    private fun loadData() {
        setState { copy(isLoading = true) }
        // load data
        setState { copy(isLoading = false, items = listOf("Item 1", "Item 2")) }
    }
}
```

### 3. Screen
```kotlin
@Composable
fun SampleScreen(
    viewModel: SampleViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SampleContract.UiEffect.ShowToast -> { /* show toast */ }
                is SampleContract.UiEffect.NavigateToDetail -> { /* navigate */ }
            }
        }
    }

    // Render UI using state
}
```