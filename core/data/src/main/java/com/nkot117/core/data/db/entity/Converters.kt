package com.nkot117.core.data.db.entity

import androidx.room.TypeConverter
import com.nkot117.core.domain.model.ItemCategory

class Converters {
    @TypeConverter
    fun toCategory(value: String): ItemCategory = ItemCategory.valueOf(value)

    @TypeConverter
    fun fromCategory(category: ItemCategory): String = category.name
}
