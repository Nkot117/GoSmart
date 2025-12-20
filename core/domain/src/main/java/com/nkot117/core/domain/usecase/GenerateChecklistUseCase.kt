package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.model.DayType
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.model.WeatherType
import com.nkot117.core.domain.repository.ItemsRepository
import com.nkot117.core.domain.repository.SpecialItemDateRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class GenerateChecklistUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val specialItemDateRepository: SpecialItemDateRepository,
) {
    /**
     * 持ち物チェックリストを生成するユースケース
     *
     * 指定された条件（勤務区分・天気・日付）に基づいて、
     * 登録されているアイテムの中から「持っていくべきもの」を抽出する。
     *
     * 抽出ルールは以下の通り：
     * - [ItemCategory.ALWAYS] : 常に含める
     * - [ItemCategory.WORKDAY] : 勤務日（[DayType.WORKDAY]）の場合に含める
     * - [ItemCategory.HOLIDAY] : 休日（[DayType.HOLIDAY]）の場合に含める
     * - [ItemCategory.RAINY] : 天気が雨（[WeatherType.RAINY]）の場合に含める
     * - [ItemCategory.SUNNY] : 天気が晴れ（[WeatherType.SUNNY]）の場合に含める
     * - [ItemCategory.DATE_SPECIFIC] : 指定された日付に紐づけられている場合のみ含める
     *
     *
     * @param dayType チェック対象日の勤務区分（勤務日 / 休日）
     * @param weatherType チェック対象日の天気（晴れ / 雨）
     * @param date チェックリストを生成する対象日
     * @return 条件を満たした Item の一覧
     */
    suspend operator fun invoke(
        dayType: DayType,
        weatherType: WeatherType,
        date: LocalDate,
    ): List<Item> {
        val all = itemsRepository.getAllItems().first()
        val specialItemIds = specialItemDateRepository.getItemIdsOnDate(date).first()
        return all.filter { item ->
            when (item.category) {
                ItemCategory.ALWAYS -> true
                ItemCategory.WORKDAY -> dayType == DayType.WORKDAY
                ItemCategory.HOLIDAY -> dayType == DayType.HOLIDAY
                ItemCategory.RAINY -> weatherType == WeatherType.RAINY
                ItemCategory.SUNNY -> weatherType == WeatherType.SUNNY
                ItemCategory.DATE_SPECIFIC -> item.id in specialItemIds
            }
        }
    }
}