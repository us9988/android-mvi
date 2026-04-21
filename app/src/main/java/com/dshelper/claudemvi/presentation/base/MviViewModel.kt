package com.dshelper.claudemvi.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

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