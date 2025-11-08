package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.repository.ItemsRepository
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class GenerateChecklistUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val specialItemDateRepository: SpecialItemDateRepository
) {
    /**
     * 持ち物チェックリストを生成するユースケース
     *
     * 与えられた条件（出勤日かどうか / 雨かどうか / 日付）に応じて、
     * 登録されているアイテムの中から、当日に必要なものだけを抽出する。
     *
     * 抽出ルールは以下の通り：
     * - ALWAYS: 常に含める
     * - WORKDAY: isWorkday が true の場合に含める
     * - HOLIDAY: isWorkday が false の場合に含める
     * - RAINY: isRain が true の場合に含める
     * - SUNNY: isRain が false の場合に含める
     * - DATE_SPECIFIC: 引数で指定された日付に紐づいている場合のみ含める
     *
     * @param isWorkday 出勤日なら true、休日なら false
     * @param isRain 当日が雨なら true、晴れなら false
     * @param date チェックリストを生成する対象日
     * @return 条件を満たした Item のリスト
     */
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