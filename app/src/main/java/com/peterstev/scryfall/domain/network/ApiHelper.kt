package com.peterstev.scryfall.domain.network

import com.peterstev.scryfall.data.models.Card
import io.reactivex.Flowable

interface ApiHelper {
    fun getCards(query: String) : Flowable<Card>
}