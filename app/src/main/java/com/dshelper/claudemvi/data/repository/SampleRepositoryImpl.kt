package com.dshelper.claudemvi.data.repository

import com.dshelper.claudemvi.data.model.SampleDto
import com.dshelper.claudemvi.domain.model.SampleModel
import com.dshelper.claudemvi.domain.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor() : SampleRepository {

    override suspend fun getSamples(): List<SampleModel> {
        return listOf(
            SampleDto(1, "Sample Item 1", "Description for item 1"),
            SampleDto(2, "Sample Item 2", "Description for item 2"),
            SampleDto(3, "Sample Item 3", "Description for item 3"),
        ).map { it.toDomain() }
    }
}
