package com.abdrabo60.productvisualizer.products.domain

class GetAllProductsUseCase(val productRepository: ProductRepository) {

    suspend operator fun invoke():List<Product>{
        return productRepository.getProducts()
    }
}