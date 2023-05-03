package com.example.expensetracker.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.ExpenseRepository

class ExpenseEditViewModel(
        savedStateHandle: SavedStateHandle
    ) : ViewModel() {
        var expenseUiState by mutableStateOf(ExpenseUiState())
            private set

        private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseEditDestination.expenseIdArg])
}