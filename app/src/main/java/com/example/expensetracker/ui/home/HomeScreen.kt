package com.example.expensetracker.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.ExpenseTopAppBar
import com.example.expensetracker.data.Expense
import com.example.expensetracker.ui.AppViewModelProvider
import com.example.expensetracker.ui.navigation.NavigationDestination
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.inventory.R
import java.text.NumberFormat

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToExpenseEntry: () -> Unit,
    navigateToExpenseUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        topBar = {
            ExpenseTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToExpenseEntry,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.expense_entry_title),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            expenseList = homeUiState.expenseList,
            onExpenseClick = navigateToExpenseUpdate,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    expenseList: List<Expense>,
    onExpenseClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExpenseListHeader()
        Divider()
        if (expenseList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_expenses),
                style = MaterialTheme.typography.subtitle2
            )
        } else {
            ExpenseList(expenseList = expenseList, onExpenseClick = { onExpenseClick(it.id) })
        }
    }
}

@Composable
private fun ExpenseList(
    expenseList: List<Expense>,
    onExpenseClick: (Expense) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = expenseList, key = { it.id }) { expense ->
            ExpenseObject(expense = expense, onExpenseClick = onExpenseClick)
            Divider()
        }
    }
}

@Composable
private fun ExpenseListHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        headerList.forEach {
            Text(
                text = stringResource(it.headerStringId),
                modifier = Modifier.weight(it.weight),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
private fun ExpenseObject(
    expense: Expense,
    onExpenseClick: (Expense) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onExpenseClick(expense) }
        .padding(vertical = 16.dp)
    ) {
        Text(
            text = expense.transactionType,
            modifier = Modifier.weight(1.5f),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = NumberFormat.getCurrencyInstance().format(expense.amount),
            modifier = Modifier.weight(1.0f)
        )
    }
}

private data class ExpenseHeader(@StringRes val headerStringId: Int, val weight: Float)

private val headerList = listOf(
    ExpenseHeader(headerStringId = R.string.transaction_type, weight = 1.5f),
    ExpenseHeader(headerStringId = R.string.amount, weight = 1.0f),
)

@Preview(showBackground = true)
@Composable
fun HomeScreenRoutePreview() {
    ExpenseTrackerTheme {
        HomeBody(
            listOf(
                Expense(1, "Deposit", 100.0),
                Expense(2, "Withdraw", -200.0),
                Expense(3, "Deposit", 300.0)
            ),
            onExpenseClick = {}
        )
    }
}