package com.nkot117.feature.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot117.core.common.toLocalDate
import com.nkot117.core.domain.model.Item
import com.nkot117.core.domain.model.ItemCategory
import com.nkot117.core.domain.model.RegisteredItemsQuery
import com.nkot117.core.domain.usecase.DeleteItemUseCase
import com.nkot117.core.domain.usecase.GetRegisteredItemListUseCase
import com.nkot117.core.domain.usecase.SaveItemUseCase
import com.nkot117.core.domain.usecase.SaveItemWithSpecialDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val getRegisteredItemListUseCase: GetRegisteredItemListUseCase,
    private val saveItemUseCase: SaveItemUseCase,
    private val saveItemWithSpecialDateUseCase: SaveItemWithSpecialDateUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
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

    fun setRegisterItemName(name: String) {
        _uiState.update {
            it.copy(
                form = ItemFormState(
                    name = name,
                    canSubmit = name.trim().isNotEmpty()
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeRegisteredItemList() {
        viewModelScope.launch {
            uiState
                .map { state ->
                    if (state.category == ItemCategory.DATE_SPECIFIC) {
                        RegisteredItemsQuery.BySpecificDate(state.date)
                    } else {
                        RegisteredItemsQuery.ByCategory(state.category)
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    getRegisteredItemListUseCase(query) // Flow<List<Item>>
                }
                .collect { items ->
                    _uiState.update { it.copy(itemList = items) }
                }
        }
    }

    fun registerItem() {
        viewModelScope.launch {
            val state = uiState.value
            val item = Item(
                name = state.form.name,
                category = state.category
            )
            when (state.category) {
                ItemCategory.DATE_SPECIFIC -> saveItemWithSpecialDateUseCase(item, state.date)
                else -> saveItemUseCase(item)
            }
        }
    }

    fun deleteItem(itemId: Long) {
        viewModelScope.launch {
            deleteItemUseCase(itemId)
        }
    }
}