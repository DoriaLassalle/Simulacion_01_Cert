package com.example.simulacion01dlassallec.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.model.ProductDetail

@Database(entities=[Product::class, ProductDetail::class], version =1)

abstract class ProductDataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: ProductDataBase? = null

        fun getDatabase(context: Context): ProductDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDataBase::class.java,
                    "product_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}