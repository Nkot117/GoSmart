package com.nkot117.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nkot117.core.data.db.dao.DailyNoteDao
import com.nkot117.core.data.db.dao.ItemsDao
import com.nkot117.core.data.db.dao.SpecialItemDateDao
import com.nkot117.core.data.db.entity.Converters
import com.nkot117.core.data.db.entity.ItemEntity
import com.nkot117.core.data.db.entity.SpecialItemDatesEntity

@Database(
    entities = [
        ItemEntity::class,
        SpecialItemDatesEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
    abstract fun specialItemDateDao(): SpecialItemDateDao
    abstract fun dailyNoteDao(): DailyNoteDao
}