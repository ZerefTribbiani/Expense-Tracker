package com.example.expensetracker.data

import kotlinx.coroutines.flow.Flow

class OfflineExpenseRepository(private val expenseDao: ExpenseDao) : ExpenseRepository {
    var totalBalance: Double = 0.0
        private set

    suspend fun refreshTotalBalance() {
        totalBalance = expenseDao.getTotalBalance() ?: 0.0
    }
    override fun getAllExpensesStream(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    override fun getExpenseStream(id: Int): Flow<Expense?> = expenseDao.getExpense(id)

    override suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)

    override suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)

    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
}