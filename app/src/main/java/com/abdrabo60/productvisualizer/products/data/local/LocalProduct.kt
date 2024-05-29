package com.abdrabo60.productvisualizer.products.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdrabo60.productvisualizer.products.data.ProductContract


@Entity(tableName = ProductContract.TABLE_NAME)
data class LocalProduct(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProductContract.ID)
    val id: Int?,
    @ColumnInfo(name = ProductContract.NAME)
    val name: String
)