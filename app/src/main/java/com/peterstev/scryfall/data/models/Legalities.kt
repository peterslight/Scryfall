package com.peterstev.scryfall.data.models

import com.google.gson.annotations.SerializedName

data class Legalities  constructor (@SerializedName("standard")
                      val standard: String = "",
                      @SerializedName("oldschool")
                      val oldschool: String = "",
                      @SerializedName("historic")
                      val historic: String = "",
                      @SerializedName("legacy")
                      val legacy: String = "",
                      @SerializedName("pioneer")
                      val pioneer: String = "",
                      @SerializedName("commander")
                      val commander: String = "",
                      @SerializedName("modern")
                      val modern: String = "",
                      @SerializedName("pauper")
                      val pauper: String = "",
                      @SerializedName("future")
                      val future: String = "",
                      @SerializedName("vintage")
                      val vintage: String = "",
                      @SerializedName("duel")
                      val duel: String = "",
                      @SerializedName("brawl")
                      val brawl: String = "",
                      @SerializedName("penny")
                      val penny: String = "")