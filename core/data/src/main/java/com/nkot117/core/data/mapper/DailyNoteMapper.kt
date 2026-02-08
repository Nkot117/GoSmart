package com.nkot117.core.data.mapper

import com.nkot117.core.data.db.entity.DailyNoteEntity
import com.nkot117.core.domain.model.DailyNote
import java.time.LocalDate

fun DailyNoteEntity.toDomain(): DailyNote = DailyNote(
    date = LocalDate.parse(date),
    text = this.text,
)

fun DailyNote.toEntity(): DailyNoteEntity = DailyNoteEntity(
    date = date.toString(),
    text = this.text,
)
