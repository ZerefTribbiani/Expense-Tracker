package com.example.expensetracker.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.expensetracker.ExpenseTrackerApplication
import com.example.expensetracker.ui.home.HomeViewModel
import com.example.expensetracker.ui.item.ExpenseDetailsViewModel
import com.example.expensetracker.ui.item.ExpenseEditViewModel
import com.example.expensetracker.ui.item.ExpenseEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ExpenseEditViewModel(
                this.createSavedStateHandle()
            )
        }

        initializer {
            ExpenseEntryViewModel(ExpenseTrackerApplication().container.expenseRepository)
        }

        initializer {
            ExpenseDetailsViewModel(
                this.createSavedStateHandle(),
                expenseApplication().container.expenseRepository
            )
        }

        initializer {
            HomeViewModel(expenseApplication().container.expenseRepository)
        }
    }
}

fun CreationExtras.expenseApplication(): ExpenseTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseTrackerApplication)