package com.nkot117.core.domain.usecase

import com.nkot117.core.domain.repository.SpecialItemDateRepository
import java.time.LocalDate


class AddSpecialItemDateUseCase(
    private val specialItemDateRepository: SpecialItemDateRepository,
) {
    /**
     * 特定の日付に持ち物を紐付けて保存するユースケース
     *
     * 「ある日にだけ持っていきたい物」を登録するために使用する。
     * 例えば「2/14 に渡すプレゼント」や「健康診断の日に必要な書類」など、
     * カレンダーに依存した持ち物を管理するケースに対応する。
     *
     * 本ユースケースは、日付とアイテムIDの組み合わせを保存するだけで、
     * アイテム自体の登録処理は行わない。
     * （新規アイテム作成と同時に特定日登録を行う場合は、
     * SaveItemWithSpecialDatesUseCase などの複合ユースケースを利用する）
     *
     * @param itemId 既に登録済みのアイテムの ID（Room により採番済みである必要あり）
     * @param date 紐付ける対象の日付
     */
    suspend operator fun invoke(
        itemId: Long,
        date: LocalDate,
    ) {
        specialItemDateRepository.saveDate(itemId, date)
    }
}