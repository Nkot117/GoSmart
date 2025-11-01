package com.nkot117.core.data.mapper

import com.nkot117.core.data.db.entity.ItemEntity
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory


fun ItemEntity.toDomain(): Item =
    Item(id = id, name = name, category = ItemCategory.valueOf(category))

fun Item.toEntity(): ItemEntity =
    ItemEntity(name = name, category = category.name)