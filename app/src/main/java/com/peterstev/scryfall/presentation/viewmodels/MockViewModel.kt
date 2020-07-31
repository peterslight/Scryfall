package com.peterstev.scryfall.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peterstev.scryfall.data.models.Data
import com.peterstev.scryfall.data.repository.MockRepository
import com.peterstev.scryfall.data.response.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MockViewModel @ViewModelInject constructor(
    private val repository: MockRepository
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<Data>>>()

    fun fetchMockData() {
        liveData.postValue(Resource.loading(null))
        repository.getMockData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ card ->
                liveData.postValue(Resource.success(card.data!!.take(10)))
            }, { error ->
                liveData.postValue(Resource.error(error.message!!, null))
            })
    }

    fun getLiveData(): MutableLiveData<Resource<List<Data>>> = liveData
}