package com.abdrabo60.productvisualizer.products.domain

class DeleteProductsUseCase(val productRepository: ProductRepository) {
    suspend operator fun invoke(products: List<Product>){
        productRepository.deleteProducts(products)
    }
}