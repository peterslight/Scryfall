package com.peterstev.scryfall.domain.network

import com.peterstev.scryfall.data.models.Card
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cards/search")
    fun getCards(@Query("q") query: String): Flowable<Card>
}