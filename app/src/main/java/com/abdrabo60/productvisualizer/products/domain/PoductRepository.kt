package com.abdrabo60.productvisualizer.products.domain

interface ProductRepository {
    suspend fun getProducts():List<Product>
     suspend fun addNewProduct(product: Product): Product
     suspend fun deleteProducts(products: List<Product>)
    suspend fun editProduct(product: Product)

}