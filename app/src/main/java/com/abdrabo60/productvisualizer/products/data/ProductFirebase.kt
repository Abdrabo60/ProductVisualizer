package com.abdrabo60.productvisualizer.products.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class ProductFirebase (
    @DocumentId
    var id:String="",
    @PropertyName(ProductContract.NAME)
    var name:String=""
    ){

}

