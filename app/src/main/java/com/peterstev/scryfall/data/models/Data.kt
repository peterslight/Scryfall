package com.peterstev.scryfall.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("oracle_text")
    val oracleText: String? = "",
    @SerializedName("set_name")
    val setName: String = "",
    @SerializedName("mana_cost")
    val manaCost: String = "",
    @SerializedName("type_line")
    val typeLine: String = "",
    @SerializedName("legalities")
    val legalities: Legalities,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("cmc")
    val cmc: Int = 0,
    @SerializedName("rarity")
    val rarity: String = "",
    @SerializedName("released_at")
    val releasedAt: String = "",
    @SerializedName("image_uris")
    val imageUrl: ImageUris?,
    @SerializedName("card_faces")
    val cardFaces: List<CardFacesItem>?
) : Serializable {
    override fun toString(): String {
        return "Data(oracleText='$oracleText', setName='$setName', manaCost='$manaCost', typeLine='$typeLine', legalities=$legalities, name='$name', cmc=$cmc, rarity='$rarity', releasedAt='$releasedAt', imageUrl=$imageUrl)"
    }
}