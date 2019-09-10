package com.app.proofofconcept.data.network

import com.app.proofofconcept.BuildConfig
import com.app.proofofconcept.data.network.reponse.FactResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 * Retrofit client for Retrofit API
 */
object ApiHelper {

    private var apiClient: Retrofit? = null

    private fun getClient(): Retrofit {
        if (apiClient == null) {
            val clientBuilder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()

            // set your desired log level
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // add logging as last interceptor
            clientBuilder.addInterceptor(loggingInterceptor)
            apiClient = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return apiClient as Retrofit
    }

    val apiService = getClient().create(ApiService::class.java)

    suspend fun getFacts(): Response<FactResponse> = apiService.getFacts()
}
