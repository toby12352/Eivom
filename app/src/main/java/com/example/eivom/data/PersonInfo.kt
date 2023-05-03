package com.example.eivom.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonInfo(
    val results: List<PersonList>
)
