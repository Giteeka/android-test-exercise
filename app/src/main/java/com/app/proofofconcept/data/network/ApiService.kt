package com.app.proofofconcept.data.network

import com.app.proofofconcept.data.network.reponse.FactResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 *
 * interface Api services for Retrofit
 */
interface ApiService {

    @GET("facts.json")
    fun getFacts(): Call<FactResponse>

}