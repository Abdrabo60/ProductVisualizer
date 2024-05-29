package com.abdrabo60.productvisualizer.core.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FirestoreRetrofitClient {
    private val BASE_URL = "https://firestore.googleapis.com/v1/projects/product-vualizing/databases/(default)/documents/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}