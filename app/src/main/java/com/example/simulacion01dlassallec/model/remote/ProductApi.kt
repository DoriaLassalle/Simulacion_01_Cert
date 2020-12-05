package com.example.simulacion01dlassallec.model.remote

import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.model.ProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/")
    suspend fun fetchProductsList(): Response<List<Product>>

    @GET("details/{id}")
    suspend fun fetchProductDetails(@Path("id") id: Int): Response<ProductDetail>

}