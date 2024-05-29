package com.abdrabo60.productvisualizer.products.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getProducts(): List<LocalProduct>


    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: String): LocalProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: LocalProduct): Long

    @Update
    fun updateProduct(product: LocalProduct)

    @Delete
    fun deleteProduct(localProduct: LocalProduct)


}