package com.nkot117.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_notes")
data class DailyNoteEntity(
    @PrimaryKey
    val date: String,
    val text: String,
)