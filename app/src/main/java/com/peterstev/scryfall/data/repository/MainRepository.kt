package com.peterstev.scryfall.data.repository

import com.peterstev.scryfall.data.models.Card
import com.peterstev.scryfall.domain.network.ApiHelper
import io.reactivex.Flowable
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getCards(query: String): Flowable<Card> = apiHelper.getCards(query)
}