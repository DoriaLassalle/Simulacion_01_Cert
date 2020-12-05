package com.example.simulacion01dlassallec.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.model.ProductDetail

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(list: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetail(details: ProductDetail)


    @Query("SELECT * FROM product")
    fun getProductList(): LiveData<List<Product>>

    @Query("SELECT * FROM prod_detail WHERE id=:id")
    fun getProductDetail(id: Int): LiveData<ProductDetail>
}