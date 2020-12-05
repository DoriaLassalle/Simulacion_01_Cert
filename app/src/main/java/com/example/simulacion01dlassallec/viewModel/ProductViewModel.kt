package com.example.simulacion01dlassallec.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.model.ProductDetail
import com.example.simulacion01dlassallec.model.ProductRepository
import com.example.simulacion01dlassallec.model.local.ProductDataBase

class ProductViewModel(application: Application): AndroidViewModel(application) {

    private val myRepository: ProductRepository

    val allProducts: LiveData<List<Product>>

    val productSelection= MutableLiveData<Int>()

    init {
        val myDao= ProductDataBase.getDatabase(application).productDao()
        myRepository= ProductRepository(myDao)
        allProducts=myRepository.productsList
        myRepository.getProductsListFromApi()
    }

    fun getProductDetails(id:Int) : LiveData<ProductDetail> {
        myRepository.getProductDetailsFromApi(id)
        return myRepository.getProductDetails(id)

    }

    fun productSelected(id: Int){
        productSelection.value= id
    }

}