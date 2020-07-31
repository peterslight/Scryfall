package com.peterstev.scryfall.data.models

import com.google.gson.annotations.SerializedName

data class ImageUris(@SerializedName("small")
                     val small: String = "",
                     @SerializedName("art_crop")
                     val artCrop: String = "")