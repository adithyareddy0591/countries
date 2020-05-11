package com.android.adithya.countries.viewmodel

import android.annotation.SuppressLint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an.github.rest.RestApi
import com.android.adithya.countries.response.CountryResponse
import com.android.adithya.countries.utils.RxSingleSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchViewModel() : ViewModel() {

    var disposable = CompositeDisposable()

    private val restApi: RestApi by lazy {
        RestApi.create()
    }
    //    private val apiData: MutableLiveData<SearchResponse> = MutableLiveData()
    private val getCountryLiveData: MutableLiveData<List<CountryResponse>> =
        MutableLiveData()
    private val apiError = MutableLiveData<Throwable>()
    private val loading = MutableLiveData<Boolean>()

//    fun apiData(): MutableLiveData<SearchResponse> {
//        return apiData
//    }

    fun apiCountryData(): MutableLiveData<List<CountryResponse>> {
        return getCountryLiveData
    }

    fun apiError(): MutableLiveData<Throwable> {
        return apiError
    }

    fun loading(): MutableLiveData<Boolean> {
        return loading
    }

    @SuppressLint("CheckResult")
    fun fetchCountrysList(query: String) {
        val observable = restApi.getCountrysList(query)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.compose(RxSingleSchedulers.DEFAULT.applySchedulers<List<CountryResponse>>())
            ?.subscribe({ loginResponse ->
                getCountryLiveData.postValue(loginResponse)

            }, { error ->
                // handle loading during error
                getCountryLiveData.postValue(ArrayList())
                apiError.value = error
            }
            )

    }


    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable.clear()
        }

    }


}


