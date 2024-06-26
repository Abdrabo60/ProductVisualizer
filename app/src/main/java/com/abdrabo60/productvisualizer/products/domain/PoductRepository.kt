package com.abdrabo60.productvisualizer.products.domain

interface ProductRepository {


    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: String): Product?

    suspend fun insertProduct(product: Product): Product

    suspend fun deleteProduct(product:Product)

    suspend fun updateProduct(product: Product)

}