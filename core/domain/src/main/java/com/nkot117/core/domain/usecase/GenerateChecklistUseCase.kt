package com.nkot117.core.domain.usecase

<<<<<<< HEAD
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.repository.ItemRepository
import kotlinx.coroutines.flow.first

class GenerateChecklistUseCase(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(
        isWorkday: Boolean,
        isRain: Boolean
    ): List<Item> {
        val all = repository.getAllItems().first()
        return all.filter { item ->
            when (item.category) {
                ItemCategory.ALWAYS -> true
                ItemCategory.WORKDAY -> isWorkday
                ItemCategory.HOLIDAY -> !isWorkday
                ItemCategory.RAINY -> isRain
                ItemCategory.SUNNY -> !isRain
                ItemCategory.DATE_SPECIFIC -> false // 後で実装
            }
        }
    }
=======
class GenerateChecklistUseCase {
>>>>>>> origin/main
}