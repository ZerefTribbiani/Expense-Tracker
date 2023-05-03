package com.example.expensetracker.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.ExpenseRepository

class ExpenseEntryViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    fun updateUiState(newExpenseUiState: ExpenseUiState) {
        expenseUiState = newExpenseUiState.copy(actionEnabled = newExpenseUiState.isValid())
    }

    suspend fun saveExpense() {
        if (expenseUiState.isValid()) {
            expenseRepository.insertExpense(expenseUiState.toExpense())
        }
    }
}