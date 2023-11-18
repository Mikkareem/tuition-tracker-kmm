package com.techullurgy.tuitiontracker.android.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.techullurgy.tuitiontracker.android.R
import com.techullurgy.tuitiontracker.android.components.AppNavHost

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                bottomNavigationItems().forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(text = navItem.label)
                        },
                        icon = {
                            if(index == 0) {
                                Icon(painter = painterResource(id = R.drawable.baseline_school_24), contentDescription = navItem.label)
                            } else {
                                Icon(painter = painterResource(id = R.drawable.baseline_assignment_24), contentDescription = navItem.label)
                            }
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                launchSingleTop = true
//                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }
    ) {
        AppNavHost(navController = navController, modifier = Modifier.padding(it))
    }
}

private data class BottomNavigationItem(
    val label: String,
    val icon: Int,
    val route: String
)

private fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Students",
            icon = R.drawable.baseline_school_24,
            route = Screen.Students.route
        ),
        BottomNavigationItem(
            label = "Activities",
            icon = R.drawable.baseline_assignment_24,
            route = Screen.Activities.route
        )
    )
}