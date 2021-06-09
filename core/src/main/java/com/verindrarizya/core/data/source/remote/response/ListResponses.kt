package com.verindrarizya.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponses(
    @field:SerializedName("results")
    val results: List<MovieResponse>
)
