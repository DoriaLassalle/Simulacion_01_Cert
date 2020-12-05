package com.example.simulacion01dlassallec

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.model.local.ProductDao
import com.example.simulacion01dlassallec.model.local.ProductDataBase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestDao {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mProductDao: ProductDao
    lateinit var db: ProductDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ProductDataBase::class.java).build()
        mProductDao = db.productDao()
    }
    @After
    fun shutDown() {
        db.close()
    }

    @Test
    fun insertBreedList() = runBlocking {

        val productsList = listOf<Product>(Product(1, "iphone", 150000, "url"))
        mProductDao.insertProductList(productsList)
        mProductDao.getProductList().observeForever {
            assertThat(it).isNotEmpty()

        }

    }
}