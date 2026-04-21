package com.dshelper.claudemvi.data.model

import com.dshelper.claudemvi.domain.model.SampleModel

data class SampleDto(
    val id: Int,
    val title: String,
    val description: String,
) {
    fun toDomain(): SampleModel = SampleModel(
        id = id,
        title = title,
        description = description,
    )
}
