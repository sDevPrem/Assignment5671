package com.sdevprem.roadcastassignment.data.entity

import com.google.gson.annotations.SerializedName
import com.sdevprem.roadcastassignment.data.datasource.constants.ApiConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MovieMeta(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDateString: String,
) {
    val posterUrl
        get() = ApiConfig.IMAGE_BASE_URL + posterPath

    val releaseDate
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .parse(releaseDateString) ?: Date()
}