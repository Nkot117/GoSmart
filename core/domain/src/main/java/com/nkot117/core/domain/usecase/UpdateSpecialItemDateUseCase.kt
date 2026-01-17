package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.repository.SpecialItemDateRepository
import java.time.LocalDate
import javax.inject.Inject

class UpdateSpecialItemDateUseCase @Inject constructor(
    private val specialItemDateRepository: SpecialItemDateRepository,
) {
    /**
     * 指定したアイテムに紐づく「特定の日付」を更新するユースケース。
     *
     * このユースケースは、`ItemCategory.DATE_SPECIFIC` のアイテムに対して
     * 既存の登録日付を新しい日付へ置き換える際に使用する。
     *
     * 本処理は以下の前提で動作します:
     * - アイテムは `itemId` でユニークに識別される
     * - 特定日付テーブルには、該当アイテムにつき 1 件の紐づきのみ存在する
     *  （つまり「1アイテム = 1日付」の前提）
     *
     * 将来的に「1アイテム = 複数日付」を許可したい場合は、
     * このユースケースではなく、
     * `AddSpecialItemDateUseCase` / `RemoveSpecialItemDateUseCase` を使用する設計へ拡張してください。
     *
     * @param itemId 日付を更新する対象のアイテムID
     * @param newDate 更新後の日付
     */
    suspend operator fun invoke(
        itemId: Long,
        newDate: LocalDate,
    ) {
        specialItemDateRepository.replaceDate(itemId, newDate)
    }
}