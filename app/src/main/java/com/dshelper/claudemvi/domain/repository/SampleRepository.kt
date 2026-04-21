package com.dshelper.claudemvi.domain.repository

import com.dshelper.claudemvi.domain.model.SampleModel

interface SampleRepository {
    suspend fun getSamples(): List<SampleModel>
}
