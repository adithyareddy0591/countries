package com.an.github.rest

import com.android.adithya.countries.AppConstants.Companion.BASE_URL
import com.android.adithya.countries.response.CountryResponse
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestApi {

    @GET("rest/v2/name/{name}")
    fun getCountrysList(@Path("name") name: String?): Single<List<CountryResponse>>

    companion object {

        private fun create(httpUrl: HttpUrl): RestApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder().addInterceptor(logging)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RestApi::class.java)
        }


        fun create(): RestApi = create(HttpUrl.parse(BASE_URL)!!)
    }

}