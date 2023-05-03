package com.example.eivom.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

data class PersonList(
    val known_for: String?,
    val known_for2: String?,
    val known_for_department: String,
    val gender: Int,
    val name: String,
    val popularity: String,
    val profile_path: String,
): java.io.Serializable

@JsonClass(generateAdapter = true)
data class PersonListJson(
    val known_for: List<KnownForListJson>,
    val known_for_department: String,
    val gender: Int,
    val name: String,
    val popularity: String,
    val profile_path: String,
)

@JsonClass(generateAdapter = true)
data class KnownForListJson(
    val poster_path: String
)

class PersonListJsonAdapter{
    @FromJson
    fun personInfoFromJson(list: PersonListJson) = PersonList(
        known_for = "https://image.tmdb.org/t/p/w500${list.known_for[0].poster_path}",
        known_for2 = "https://image.tmdb.org/t/p/w500${list.known_for[1].poster_path}",
        known_for_department = "Known For: ${list.known_for_department}",
        gender = list.gender,
        name = list.name,
        popularity = "Popularity: ${list.popularity.toBigDecimal().toPlainString()}",
        profile_path = "https://image.tmdb.org/t/p/w500${list.profile_path}"
    )

    @ToJson
    fun personListToJson(personList: PersonList): String{
        throw UnsupportedOperationException("Encoding PersonList to Json is not supported")
    }
}

