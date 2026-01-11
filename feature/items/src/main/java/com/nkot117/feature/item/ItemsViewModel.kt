package com.nkot117.feature.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot117.core.common.toLocalDate
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.usecase.GetRegisteredItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val getRegisteredItemListUseCase: GetRegisteredItemListUseCase,
) : ViewModel() {
    /**
     * UiState
     */
    private val _uiState = MutableStateFlow(ItemsUiState())
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()

    fun setDate(selectedDate: Long) {
        val localDate = selectedDate.toLocalDate()
        _uiState.update { it.copy(date = localDate) }
    }

    fun setCategory(category: ItemCategory) {
        _uiState.update { it.copy(category = category) }
    }

    fun getRegisteredItemList() {
        viewModelScope.launch {
            val state = uiState.value
            val query = if (state.category == ItemCategory.DATE_SPECIFIC) {
                RegisteredItemsQuery.BySpecificDate(date = state.date)
            } else {
                RegisteredItemsQuery.ByCategory(category = state.category)
            }
            val items = getRegisteredItemListUseCase(query)
            _uiState.update { it.copy(itemList = items) }
        }
    }
}