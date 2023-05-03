package com.example.eivom.data

import android.icu.util.UniversalTimeScale.toBigDecimal
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.math.RoundingMode

@Entity
data class TvShowList(
    val adult : Boolean,
    val backdrop_path: String,
    @PrimaryKey
    val name : String,
    val original_language : String,
    val overview : String,
    val poster_path: String?,
    val first_air_date: String,
    val vote_average: String
): java.io.Serializable

@JsonClass(generateAdapter = true)
data class TvShowListJson(
    val adult : Boolean,
    val backdrop_path: String,
    val name : String,
    val original_language : String,
    val overview : String,
    val poster_path: String?,
    val first_air_date: String,
    val vote_average: Double
)

class TvShowListJsonAdapter{
    @FromJson
    fun tvShowInfoFromJson(list: TvShowListJson) = TvShowList(
        adult = list.adult,
        backdrop_path = "https://image.tmdb.org/t/p/w500${list.backdrop_path}",
        name = list.name,
        original_language = list.original_language,
        overview = list.overview,
        poster_path = "https://image.tmdb.org/t/p/w500${list.poster_path}",
        first_air_date = "Release Date: ${list.first_air_date}",
        vote_average = "Rating: ${list.vote_average.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toPlainString()}/10"
    )

    @ToJson
    fun tvShowListToJson(list: TvShowList): String{
        throw java.lang.UnsupportedOperationException("Encoding TvShowList to Json is not supported")
    }
}