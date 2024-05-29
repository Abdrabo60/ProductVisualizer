package com.abdrabo60.productvisualizer.products.data.local

import com.abdrabo60.productvisualizer.products.data.datasource.ProductDataSource
import com.abdrabo60.productvisualizer.products.domain.Product

class LocalProductSourceImpl(val productDao: ProductDao):ProductDataSource<LocalProduct> {
    override suspend fun getProducts(): List<LocalProduct> {
        return productDao.getProducts()
    }

    override suspend fun getProduct(id: String): LocalProduct? {
        return productDao.getProduct(id)
    }

    override suspend fun insertProduct(product: LocalProduct): LocalProduct {
        return product.copy(id=productDao.insertProduct(product).toInt())
    }

    override suspend fun updateProduct(product: LocalProduct) {
        return productDao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: LocalProduct) {
        return productDao.deleteProduct(product)
    }
}