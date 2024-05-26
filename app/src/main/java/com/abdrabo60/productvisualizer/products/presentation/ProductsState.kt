package com.abdrabo60.productvisualizer.products.presentation

data class ProductsState(
    val products: List<UIProduct>,
    val isLoading: Boolean,
    val error: String? = null,
)
