package com.abdrabo60.productvisualizer.products.domain

class EditProductUseCase(val productRepository: ProductRepository) {

    suspend operator fun invoke( product: Product){
        productRepository.editProduct(product)
    }
}