package com.nkot117.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nkot117.core.data.db.dao.ItemsDao
import com.nkot117.core.data.db.entity.Converters
import com.nkot117.core.data.db.entity.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
}