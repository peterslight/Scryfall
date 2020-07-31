package com.peterstev.scryfall.data.models

import com.google.gson.annotations.SerializedName

data class CardFacesItem(
    @SerializedName("image_uris") val imageUris: ImageUris,
    @SerializedName("oracle_text")
    val oracleText: String? = ""
)