package com.abdrabo60.productvisualizer.products.data

import com.abdrabo60.productvisualizer.products.data.remote.ProductFields
import com.abdrabo60.productvisualizer.products.data.remote.ProductApiService
import com.abdrabo60.productvisualizer.core.data.FirestoreRetrofitClient
import com.abdrabo60.productvisualizer.products.data.remote.ProductDocument
import com.abdrabo60.productvisualizer.products.domain.Product
import com.abdrabo60.productvisualizer.products.domain.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    val api = FirestoreRetrofitClient().getRetrofitInstance().create(ProductApiService::class.java)

    override suspend fun getProducts(): List<Product> {
        val apiProduct = api.getProducts()
        val domainProducts= apiProduct.documents.map {
            Product(it.name?.split("/")?.last() ?: "",
                it.fields.get(ProductContract.NAME)?.let { it.stringValue } ?: "")
        }

        return domainProducts
    }

    override suspend fun insertProduct(product: Product): Product {
        val mapOf = mapOf(ProductContract.NAME to ProductFields(product.name))
        val productDocument = ProductDocument(fields = mapOf)
        val productDocumented = api.addNewProduct(productDocument)
        return product.copy(id = productDocumented.name?.split("/")?.last() ?: "")
    }

    override suspend fun deleteProduct(product: Product) {
        api.deleteProducts(product.id)
    }

    override suspend fun updateProduct(product: Product) {
        api.updateProduct(
            product.id, product = ProductDocument(
                name = product.id, fields = mapOf(ProductContract.NAME to ProductFields(product.name))
            )
        )
    }


}