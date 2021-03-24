package com.killkinto.zapplus.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/sources/"
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

class PropertyApi(private val retrofit: Retrofit) {
    val retrofitService: GrupoZapService by lazy {
        retrofit.create(GrupoZapService::class.java)
    }
}

interface GrupoZapService {
    @GET("source-1.json")
    //@GET("source-sample.json")
    suspend fun getProperties() : List<NetworkProperty>
}