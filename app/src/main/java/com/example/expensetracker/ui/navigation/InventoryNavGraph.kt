package com.example.expensetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.ui.home.HomeDestination
import com.example.expensetracker.ui.home.HomeScreen
import com.example.expensetracker.ui.item.ExpenseDetailsDestination
import com.example.expensetracker.ui.item.ExpenseDetailsScreen
import com.example.expensetracker.ui.item.ExpenseEditDestination
import com.example.expensetracker.ui.item.ExpenseEditScreen
import com.example.expensetracker.ui.item.ExpenseEntryDestination
import com.example.expensetracker.ui.item.ExpenseEntryScreen

@Composable
fun ExpenseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToExpenseEntry = { navController.navigate(ExpenseEntryDestination.route) },
                navigateToExpenseUpdate = {
                    navController.navigate("${ExpenseDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = ExpenseEntryDestination.route) {
            ExpenseEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ExpenseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseDetailsDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseDetailsScreen(
                navigateToEditExpense = { navController.navigate("${ExpenseEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ExpenseEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseEditDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}