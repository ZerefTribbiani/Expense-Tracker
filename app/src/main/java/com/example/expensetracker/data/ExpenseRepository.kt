package com.example.expensetracker.data

import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpensesStream(): Flow<List<Expense>>

    fun getExpenseStream(id: Int): Flow<Expense?>

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)
}
