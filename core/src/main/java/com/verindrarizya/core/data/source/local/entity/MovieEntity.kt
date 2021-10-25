package com.verindrarizya.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "poster")
    var poster: String,

    @ColumnInfo(name = "release_date")
    var date: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

)