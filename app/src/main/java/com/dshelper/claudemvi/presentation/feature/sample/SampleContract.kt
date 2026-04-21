package com.dshelper.claudemvi.presentation.feature.sample

import com.dshelper.claudemvi.domain.model.SampleModel
import com.dshelper.claudemvi.presentation.base.MviEffect
import com.dshelper.claudemvi.presentation.base.MviEvent
import com.dshelper.claudemvi.presentation.base.MviState

interface SampleContract {

    sealed class UiEvent : MviEvent {
        data object LoadSamples : UiEvent()
        data class OnItemClick(val item: SampleModel) : UiEvent()
    }

    data class UiState(
        val isLoading: Boolean = false,
        val items: List<SampleModel> = emptyList(),
        val error: String? = null,
    ) : MviState

    sealed class UiEffect : MviEffect {
        data class ShowToast(val message: String) : UiEffect()
    }
}
