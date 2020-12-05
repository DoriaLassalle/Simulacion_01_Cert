package com.example.simulacion01dlassallec.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRetrofitClient {


    companion object{
        private const val BASE_URL= "http://my-json-server.typicode.com/Himuravidal/FakeAPIdata/"

        fun retrofitInstance(): ProductApi {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitClient.create(ProductApi::class.java)
        }

    }
}