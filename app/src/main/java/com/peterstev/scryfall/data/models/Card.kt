package com.peterstev.scryfall.data.models

import com.google.gson.annotations.SerializedName

data class Card(@SerializedName("total_cards")
                val totalCards: Int = 0,
                @SerializedName("data")
                val data: List<Data>?)