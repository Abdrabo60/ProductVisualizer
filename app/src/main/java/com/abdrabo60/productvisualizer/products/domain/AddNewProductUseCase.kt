package com.abdrabo60.productvisualizer.products.domain

class AddNewProductUseCase( val productRepository: ProductRepository) {

    suspend operator fun invoke(product:Product):Product{
        return productRepository.insertProduct(product)
    }
}