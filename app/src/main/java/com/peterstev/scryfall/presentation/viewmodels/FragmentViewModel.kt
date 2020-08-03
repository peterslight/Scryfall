package com.peterstev.scryfall.presentation.viewmodels

import android.annotation.SuppressLint
import android.os.Debug
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peterstev.scryfall.data.models.Data
import com.peterstev.scryfall.data.repository.MainRepository
import com.peterstev.scryfall.data.response.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@SuppressLint("CheckResult")
class FragmentViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<Data>>>()
    private val selectedCard = MutableLiveData<Data>()

    fun fetchData(query: String) {
        liveData.postValue(Resource.loading(null))
        mainRepository.getCards(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ card ->
                liveData.postValue(Resource.success(card.data!!.take(10)))
            }, { error ->
                liveData.postValue(Resource.error(error.message!!, null))
            })
    }

    fun getLiveData(): MutableLiveData<Resource<List<Data>>> = liveData
    fun getSelectedCardItem(): Data = selectedCard.value!!
    fun setSelectedCardItem(data: Data) = selectedCard.postValue(data)
}