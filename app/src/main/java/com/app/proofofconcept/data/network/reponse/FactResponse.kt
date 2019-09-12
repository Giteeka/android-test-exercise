package com.app.proofofconcept.data.network.reponse

import com.app.proofofconcept.data.model.RowItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FactResponse(
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("rows")
    @Expose
    val rows: List<RowItem>
)