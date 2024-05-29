package com.abdrabo60.productvisualizer.products.data.remote


data class ProductCollection(val documents:List<ProductDocument>)
data class ProductDocument(
    val name: String?=null,
    val fields: Map<String, ProductFields>,
    val createTime: String?=null,
    val updateTime: String?=null
)

data class ProductFields(val stringValue: String)

