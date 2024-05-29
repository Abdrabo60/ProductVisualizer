package com.abdrabo60.productvisualizer.products.data.remote

import com.abdrabo60.productvisualizer.products.data.ProductContract
import com.abdrabo60.productvisualizer.products.data.datasource.ProductDataSource
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path




interface ProductApiService{

    @GET(ProductContract.TABLE_NAME)
     suspend fun getProducts(): ProductCollection

    @GET(ProductContract.TABLE_NAME+"/{document_id}")
     suspend fun getProduct(@Path("document_id")id: String): ProductDocument?


    @POST(ProductContract.TABLE_NAME)
     suspend fun insertProduct(@Body product: ProductDocument):ProductDocument

    @DELETE(ProductContract.TABLE_NAME+"/{DocumentId}")
     suspend fun deleteProduct(@Path("DocumentId") id:String)

    @PATCH(ProductContract.TABLE_NAME+"/{DocumentId}")
     suspend fun updateProduct(@Path("DocumentId")id: String, @Body product: ProductDocument)

}