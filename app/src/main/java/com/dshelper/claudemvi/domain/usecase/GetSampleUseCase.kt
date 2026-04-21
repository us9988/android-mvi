package com.dshelper.claudemvi.domain.usecase

import com.dshelper.claudemvi.domain.model.SampleModel
import com.dshelper.claudemvi.domain.repository.SampleRepository
import javax.inject.Inject

class GetSampleUseCase @Inject constructor(
    private val repository: SampleRepository
) {
    suspend operator fun invoke(): List<SampleModel> = repository.getSamples()
}
