package com.abdrabo60.productvisualizer.products.domain

class DeleteProductUseCase(val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product){
        productRepository.deleteProduct(product)
    }
}