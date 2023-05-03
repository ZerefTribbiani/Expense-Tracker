package com.example.expensetracker.ui.item

import com.example.expensetracker.data.Expense

data class ExpenseUiState(
    val id: Int = 0,
    val transactionType: String = "",
    val amount: String = "",
    val actionEnabled: Boolean = false
)

fun ExpenseUiState.toExpense(): Expense = Expense(
    id = id,
    transactionType = transactionType,
    amount = amount.toDouble(),
)

fun Expense.toExpenseUiState(actionEnabled: Boolean = false): ExpenseUiState = ExpenseUiState(
    id = id,
    transactionType = transactionType,
    amount = amount.toString(),
    actionEnabled = actionEnabled
)

fun ExpenseUiState.isValid() : Boolean {
    return transactionType.isNotBlank() && amount.isNotBlank()
}
