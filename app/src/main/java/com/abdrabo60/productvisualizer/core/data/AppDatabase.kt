package com.abdrabo60.productvisualizer.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdrabo60.productvisualizer.products.data.local.LocalProduct
import com.abdrabo60.productvisualizer.products.data.local.ProductDao

@Database(entities = [LocalProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
object DatabaseProvider {
    private var db: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        if (db == null) {
            synchronized(AppDatabase::class) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build()
            }
        }
        return db!!
    }
}