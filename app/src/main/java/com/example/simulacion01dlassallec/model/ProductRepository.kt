package com.example.simulacion01dlassallec.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.simulacion01dlassallec.model.local.ProductDao
import com.example.simulacion01dlassallec.model.remote.ProductRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductRepository (private val myProductDao: ProductDao){

    private val myRetrofit= ProductRetrofitClient.retrofitInstance()

    val productsList=myProductDao.getProductList()

    fun getProductDetails(id:Int): LiveData<ProductDetail> {
        return myProductDao.getProductDetail(id)
    }

    fun getProductsListFromApi()= CoroutineScope(Dispatchers.IO).launch {

        val service= kotlin.runCatching { myRetrofit.fetchProductsList() }
        service.onSuccess {
            when(it.code()) {
                in 200..299 -> it.body()?.let {

                    myProductDao.insertProductList(it)
                }

                in 300..599 -> Log.e("ERROR", it.errorBody().toString())
                else -> Log.d("ERROR", it.errorBody().toString())
            }
        }

        service.onFailure {
            Log.e("ERROR", it.message.toString())

        }
    }

    fun getProductDetailsFromApi(id:Int)= CoroutineScope(Dispatchers.IO).launch {
        val service= kotlin.runCatching { myRetrofit.fetchProductDetails(id)}
        service.onSuccess {
            when(it.code()) {
                in 200..299 -> it.body()?.let {

                    myProductDao.insertProductDetail(it)
                }

                in 300..599 -> Log.e("ERROR", it.errorBody().toString())
                else -> Log.d("ERROR", it.errorBody().toString())
            }
        }

        service.onFailure {
            Log.e("ERROR", it.message.toString())

        }

    }
}