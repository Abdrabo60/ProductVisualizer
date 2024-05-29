package com.abdrabo60.productvisualizer.products.presentation

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdrabo60.productvisualizer.MyApp
import com.abdrabo60.productvisualizer.core.data.AppDatabase
import com.abdrabo60.productvisualizer.core.data.FirestoreRetrofitClient
import com.abdrabo60.productvisualizer.core.data.LocalDatabaseProvider
import com.abdrabo60.productvisualizer.products.data.ProductRepositoryImpl
import com.abdrabo60.productvisualizer.products.data.local.LocalProductSourceImpl
import com.abdrabo60.productvisualizer.products.data.local.ProductDao
import com.abdrabo60.productvisualizer.products.domain.AddNewProductUseCase
import com.abdrabo60.productvisualizer.products.domain.DeleteProductUseCase
import com.abdrabo60.productvisualizer.products.domain.EditProductUseCase
import com.abdrabo60.productvisualizer.products.domain.GetAllProductsUseCase
import com.abdrabo60.productvisualizer.products.domain.Product
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel() : ViewModel() {
    private val productRepository = ProductRepositoryImpl(LocalProductSourceImpl(LocalDatabaseProvider.
    provideDatabase(MyApp.instance).productDao()))

    private val getAllProductsUseCase = GetAllProductsUseCase(productRepository)
    private val addNewProductUseCase = AddNewProductUseCase(productRepository)
    private val deleteProductUseCase = DeleteProductUseCase(productRepository)
    private val editProductUseCase = EditProductUseCase(productRepository)
    private var _state = mutableStateOf(
        ProductsState(
            products = emptyList(), isLoading = true
        )
    )

    val state: State<ProductsState>
        get() = derivedStateOf { _state.value }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state.value = _state.value.copy(
            isLoading = false, error = throwable.message
        )
    }

    init {
        getProducts()

    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO+errorHandler) {
            val receivedProducts = getAllProductsUseCase()
            _state.value = _state.value.copy(
                products = receivedProducts.map { UIProduct(it.id, it.name) },
                isLoading = false,
            )

        }
    }

    fun addNewProduct(product: Product) {
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO+errorHandler) {
            val p: Product = addNewProductUseCase(product)
            val list = _state.value.products
            _state.value = _state.value.copy(
                products = list.plus(UIProduct(p.id, p.name)), isLoading = false
            )
        }

    }

    fun onToggleSelection(index: Int) {
        val list = _state.value.products.mapIndexed { i: Int, uiProduct: UIProduct ->
            if (i == index) {
                UIProduct(id = uiProduct.id, name = uiProduct.name, selected = !uiProduct.selected)
            } else {
                uiProduct
            }
        }
        _state.value = _state.value.copy(products = list)
    }

    fun onClearAllSelection() {
        val list = _state.value.products.map { it.copy(selected = false) }
        _state.value = _state.value.copy(products = list)
    }

    fun onDeleteProduct() {

        viewModelScope.launch(Dispatchers.IO+errorHandler) {
            val selected = _state.value.products.filter { it.selected }
            val domainModels = selected.map { Product(it.id, it.name) }
            domainModels.forEach { product ->
                _state.value = _state.value.copy(isLoading = true)
                deleteProductUseCase(product)
                val newList = _state.value.products.minus(selected.filter { it.id == product.id })
                _state.value = _state.value.copy(products = newList, isLoading = false)
            }


        }


    }

    fun onEditProduct(product: Product) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO+errorHandler) {
            editProductUseCase(product)
            val list = _state.value.products.map() { item ->
                if (item.id == product.id) {
                    item.copy(name = product.name)
                } else {
                    item
                }
            }
            _state.value = _state.value.copy(products = list, isLoading = false)
        }
    }


}