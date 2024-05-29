package com.abdrabo60.productvisualizer.products.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abdrabo60.productvisualizer.products.data.datasource.ProductDataSource


@Dao
interface ProductDao:ProductDataSource<LocalProduct> {

    @Query("SELECT * FROM products")
    override suspend fun getProducts(): List<LocalProduct>


    @Query("SELECT * FROM products WHERE id = :id")
    override suspend fun getProduct(id: String): LocalProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertProduct(product: LocalProduct):LocalProduct

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products:List<LocalProduct>)

    @Update
    suspend fun updateProduct( product: LocalProduct)

    @Delete
    suspend fun deleteProduct(localProduct: LocalProduct)


}