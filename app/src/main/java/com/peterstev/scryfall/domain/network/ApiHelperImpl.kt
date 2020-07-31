package com.peterstev.scryfall.domain.network

import com.peterstev.scryfall.data.models.Card
import io.reactivex.Flowable
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override fun getCards(query: String): Flowable<Card> = apiService.getCards(query)
}