package com.abdrabo60.productvisualizer.products.data

import androidx.compose.ui.util.trace
import com.abdrabo60.productvisualizer.products.domain.Product
import com.abdrabo60.productvisualizer.products.domain.ProductRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(firebaseFirestore: FirebaseFirestore) : ProductRepository {

    private val collectionReference: CollectionReference =
        firebaseFirestore.collection(ProductContract.TABLE_NAME)

    override suspend fun getProducts(): List<Product> {
        val querySnapshot: QuerySnapshot = collectionReference.get().await()
        val objects = querySnapshot.toObjects(ProductFirebase::class.java)
        return objects.map { Product(id = it.id, it.name) }
    }

    override suspend fun addNewProduct(product: Product): Product {
        val productFirebase = ProductFirebase(name = product.name)
        val documentReference = collectionReference.add(productFirebase).await()
        productFirebase.id = documentReference.id
        return productFirebase.let { Product(it.id, it.name) }

    }

    override suspend fun deleteProducts(products: List<Product>) {
         collectionReference.firestore.runTransaction {transaction->
             products.forEach { product->
             val document = collectionReference.document(product.id)
             transaction.delete(document)
             }
         }.await()
    }

    override suspend fun editProduct(product: Product) {
        val firebaseProduct=ProductFirebase(product.id,product.name)
        collectionReference.document(firebaseProduct.id).set(firebaseProduct).await()

    }

}