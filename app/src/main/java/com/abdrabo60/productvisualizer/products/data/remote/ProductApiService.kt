package com.abdrabo60.productvisualizer.products.data.remote

import com.abdrabo60.productvisualizer.products.data.ProductContract
import com.abdrabo60.productvisualizer.products.data.datasource.ProductDataSource
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService:ProductDataSource<ProductDocument> {

    @GET(ProductContract.TABLE_NAME)
    override suspend fun getProducts(): List<ProductDocument>

    @GET(ProductContract.TABLE_NAME+"/{document_id}")
    override suspend fun getProduct(@Path("document_id")id: String): ProductDocument



    @POST(ProductContract.TABLE_NAME)
    override suspend fun insertProduct(@Body product: ProductDocument):ProductDocument

    @DELETE(ProductContract.TABLE_NAME+"/{DocumentId}")
    override suspend fun deleteProduct(@Path("DocumentId") id:String)

    @PATCH(ProductContract.TABLE_NAME+"/{DocumentId}")
    override suspend fun updateProduct(@Path("DocumentId")id: String, @Body product: ProductDocument)

}