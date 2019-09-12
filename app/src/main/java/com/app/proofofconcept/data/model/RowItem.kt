package com.app.proofofconcept.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RowItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String?,
    val description: String?,
    val imageHref: String?
)