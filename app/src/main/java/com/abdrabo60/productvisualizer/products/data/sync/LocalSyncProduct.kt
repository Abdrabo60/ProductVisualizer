package com.abdrabo60.productvisualizer.products.data.sync

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdrabo60.productvisualizer.products.data.ProductContract

@Entity(tableName = ProductContract.TABLE_NAME)
data class LocalSyncProduct(
    @ColumnInfo(name = ProductContract.ID)
    val id: String,
    @ColumnInfo(name = ProductContract.NAME)
    val name: String,
    @ColumnInfo(name= SyncContract.OPERATION)
    val operation:String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= SyncContract.INDEX)
    val index:Int
)
