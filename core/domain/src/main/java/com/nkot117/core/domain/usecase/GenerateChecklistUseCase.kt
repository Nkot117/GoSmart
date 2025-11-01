package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.repository.ItemsRepository
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class GenerateChecklistUseCase(
    private val itemsRepository: ItemsRepository,
    private val specialItemDateRepository: SpecialItemDateRepository
) {
    suspend operator fun invoke(
        isWorkday: Boolean,
        isRain: Boolean,
        date: LocalDate
    ): List<Item> {
        val all = itemsRepository.getAllItems().first()
        val specialItemIds = specialItemDateRepository.getItemIdsOnDate(date).first()
        return all.filter { item ->
            when (item.category) {
                ItemCategory.ALWAYS -> true
                ItemCategory.WORKDAY -> isWorkday
                ItemCategory.HOLIDAY -> !isWorkday
                ItemCategory.RAINY -> isRain
                ItemCategory.SUNNY -> !isRain
                ItemCategory.DATE_SPECIFIC -> item.id in specialItemIds
            }
        }
    }
}