package com.dshelper.claudemvi.presentation.feature.sample

import androidx.lifecycle.viewModelScope
import com.dshelper.claudemvi.domain.usecase.GetSampleUseCase
import com.dshelper.claudemvi.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getSampleUseCase: GetSampleUseCase,
) : MviViewModel<
    SampleContract.UiEvent,
    SampleContract.UiState,
    SampleContract.UiEffect
>(SampleContract.UiState()) {

    override fun onEvent(event: SampleContract.UiEvent) {
        when (event) {
            is SampleContract.UiEvent.LoadSamples -> loadSamples()
            is SampleContract.UiEvent.OnItemClick -> onItemClick(event)
        }
    }

    private fun loadSamples() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            runCatching { getSampleUseCase() }
                .onSuccess { items ->
                    setState { copy(isLoading = false, items = items) }
                    sendEffect(SampleContract.UiEffect.ShowToast("Loaded ${items.size} items"))
                }
                .onFailure { e ->
                    setState { copy(isLoading = false, error = e.message) }
                }
        }
    }

    private fun onItemClick(event: SampleContract.UiEvent.OnItemClick) {
        viewModelScope.launch {
            sendEffect(SampleContract.UiEffect.ShowToast("Clicked: ${event.item.title}"))
        }
    }
}
