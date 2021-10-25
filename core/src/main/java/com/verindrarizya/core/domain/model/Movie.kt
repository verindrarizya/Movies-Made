package com.verindrarizya.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var poster: String,
    var date: String,
    var rating: Double,
    var overview: String,
    var isFavorite: Boolean = false,
): Parcelable
