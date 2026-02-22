package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikko1.data.local.AppDatabase
import com.example.viikko1.data.repository.TaskRepository
import com.example.viikko1.navigation.ROUTE_CALENDAR
import com.example.viikko1.navigation.ROUTE_HOME
import com.example.viikko1.ui.theme.Viikko1Theme
import com.example.viikko1.view.CalendarScreen
import com.example.viikko1.view.TaskScreen
import com.example.viikko1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }
    private val repository by lazy {
        TaskRepository(database.TaskDao())
    }
    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            Viikko1Theme {
                NavHost(
                    navController = navController,
                    startDestination = ROUTE_HOME
                ) {

                    composable(ROUTE_HOME) {
                        TaskScreen(
                            viewModel = viewModel,
                            onNavigateCalendar = { navController.navigate(ROUTE_CALENDAR) }
                        )
                    }
                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            viewModel = viewModel,
                            onNavigateHome = { navController.navigate(ROUTE_HOME) }
                        )
                    }
                }
            }
        }
    }
}
