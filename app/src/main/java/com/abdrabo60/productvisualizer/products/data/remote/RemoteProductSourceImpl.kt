package com.abdrabo60.productvisualizer.products.data.remote

import com.abdrabo60.productvisualizer.products.data.datasource.ProductDataSource

class RemoteProductSourceImpl(val api: ProductApiService) : ProductDataSource<ProductDocument> {

    override suspend fun getProducts(): List<ProductDocument> {
        return api.getProducts().documents.map { it.copy(name = it.name!!.split("/").last()) }
    }

    override suspend fun getProduct(id: String): ProductDocument? {
        return api.getProduct(id)?.let { it.copy(name = it.name?.split("/")?.last()) }

    }

    override suspend fun insertProduct(product: ProductDocument): ProductDocument {
        return api.insertProduct(product)
    }

    override suspend fun deleteProduct(product: ProductDocument) {
        return api.deleteProduct(product.name!!)
    }

    override suspend fun updateProduct(product: ProductDocument) {
        return api.updateProduct(product.name!!, product)
    }

}