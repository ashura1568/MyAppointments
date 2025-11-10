package com.example.myappointments.io

import com.example.myappointments.model.Specialty
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("specialties")
    abstract fun getSpecialties(): Call<ArrayList<Specialty>>


    companion object Factory {
        // Local IP to use on an emulator
        //php artisan serve --host=0.0.0.0
        //private const val BASE_URL = "http://10.0.2.2:8000/api/"

         private const val BASE_URL = "http://45.55.225.164/api/"

        fun create(): ApiService {
            //val interceptor = HttpLoggingInterceptor()
            //interceptor.level = HttpLoggingInterceptor.Level.BODY
            //val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}