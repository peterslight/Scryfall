package com.peterstev.scryfall.data.repository

import com.google.gson.Gson
import com.peterstev.scryfall.data.models.Card
import com.peterstev.scryfall.domain.mock.MOCK_DATA
import io.reactivex.Flowable
import javax.inject.Inject

class MockRepository @Inject constructor() {

    fun getMockData(): Flowable<Card> {
        val data = Gson().fromJson(MOCK_DATA, Card::class.java)
        return Flowable.just(data)
    }

    fun getRawMockData(): Card {
        return Gson().fromJson(MOCK_DATA, Card::class.java)
    }
}