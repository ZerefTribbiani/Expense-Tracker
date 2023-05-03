package com.example.expensetracker.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ExpenseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseDetailsDestination.expenseIdArg])
    val uiState: StateFlow<ExpenseUiState> =
        expenseRepository.getExpenseStream(expenseId)
            .filterNotNull()
            .map {
                it.toExpenseUiState()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExpenseUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}