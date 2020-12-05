package com.example.simulacion01dlassallec

import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.model.ProductDetail
import com.example.simulacion01dlassallec.model.remote.ProductApi
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestApi {

    lateinit var mMockWebServer: MockWebServer
    lateinit var mProductTestApi: ProductApi

    @Before
    fun setUp() {
        mMockWebServer = MockWebServer()
        val mRetrofit = Retrofit.Builder()
            .baseUrl(mMockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mProductTestApi = mRetrofit.create(ProductApi::class.java)
    }

    @After
    fun shutDown() {
        mMockWebServer.shutdown()
    }

    @Test
    fun getProductDetails()= runBlocking {

        val productDetail = ProductDetail(1, "Samsung", 240000, "url",
            "Galaxy Note", 225000, false)
        val productId = 1
        mMockWebServer.enqueue(MockResponse().setBody(Gson().toJson(productDetail)))

        val result = mProductTestApi.fetchProductDetails(productId)

        val body = result.body()
        assertThat(body?.credit).isEqualTo(false)
        assertThat(body?.name).isEqualTo("Samsung")
    }

    @Test
    fun getAllProducts() = runBlocking {

        val productTest = Product(1,"Samsung",240000,"urlx")

        mMockWebServer.enqueue(MockResponse().setBody(Gson().toJson(productTest)))

        val result = mProductTestApi.fetchProductsList()

        assertThat(result).isNotNull()
        val body = result.body()
        if (body != null) {
            assertThat(body.size).isEqualTo(1)

        }


        val request = mMockWebServer.takeRequest()
        println(request.path)
        Truth.assertThat(request.path).isEqualTo("products/")
    }



}