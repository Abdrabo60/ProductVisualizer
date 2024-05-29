package com.abdrabo60.productvisualizer.products.data

import com.abdrabo60.productvisualizer.products.data.remote.ProductFields
import com.abdrabo60.productvisualizer.products.data.remote.ProductApiService
import com.abdrabo60.productvisualizer.core.data.FirestoreRetrofitClient
import com.abdrabo60.productvisualizer.products.data.local.LocalProduct
import com.abdrabo60.productvisualizer.products.data.local.LocalProductSourceImpl
import com.abdrabo60.productvisualizer.products.data.local.ProductDao
import com.abdrabo60.productvisualizer.products.data.remote.ProductDocument
import com.abdrabo60.productvisualizer.products.data.remote.RemoteProductSourceImpl
import com.abdrabo60.productvisualizer.products.domain.Product
import com.abdrabo60.productvisualizer.products.domain.ProductRepository

class ProductRepositoryImpl(
    val localSource: LocalProductSourceImpl
) : ProductRepository {


    override suspend fun getProducts(): List<Product> {
        return localSource.getProducts().map { Product(it.id.toString() , it.name) }
    }

    override suspend fun getProduct(id: String): Product? {
        val localProduct = localSource.getProduct(id)
        return if (localProduct != null) {
            Product(localProduct.id.toString(), localProduct.name)
        } else {
            null
        }
    }

    override suspend fun insertProduct(product: Product): Product {
        val insertProduct = localSource.insertProduct(LocalProduct(product.id.toIntOrNull(), product.name))
        return Product(insertProduct.id.toString() , insertProduct.name)
    }

    override suspend fun deleteProduct(product: Product) {
        localSource.deleteProduct(LocalProduct(product.id.toInt(), product.name))
    }

    override suspend fun updateProduct(product: Product) {
        localSource.updateProduct(LocalProduct(product.id.toInt(), product.name))
    }


}