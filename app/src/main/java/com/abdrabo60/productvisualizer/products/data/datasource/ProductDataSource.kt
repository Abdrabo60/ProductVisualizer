package com.abdrabo60.productvisualizer.products.data.datasource

import com.abdrabo60.productvisualizer.products.domain.Product
import com.abdrabo60.productvisualizer.products.domain.ProductRepository

interface ProductDataSource<Product> {
    suspend fun getProducts(): List<Product>

    suspend fun getProduct(id: String): Product?

    suspend fun insertProduct(product:Product): Product

    suspend fun deleteProduct(product: Product)

    suspend fun updateProduct(product: Product)

}