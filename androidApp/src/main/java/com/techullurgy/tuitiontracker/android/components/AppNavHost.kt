package com.techullurgy.tuitiontracker.android.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.techullurgy.tuitiontracker.android.screens.AddStudentScreen
import com.techullurgy.tuitiontracker.android.screens.AddWorkActivityScreen
import com.techullurgy.tuitiontracker.android.screens.Screen
import com.techullurgy.tuitiontracker.android.screens.StudentDetailScreen
import com.techullurgy.tuitiontracker.android.screens.StudentListScreen
import com.techullurgy.tuitiontracker.android.screens.WorkActivitiesListScreen
import com.techullurgy.tuitiontracker.android.screens.WorkActivityDetailScreen
import com.techullurgy.tuitiontracker.android.viewmodels.StudentDetailsViewModel
import com.techullurgy.tuitiontracker.android.viewmodels.StudentsViewModel
import com.techullurgy.tuitiontracker.android.viewmodels.WorkActivitiesViewModel
import com.techullurgy.tuitiontracker.android.viewmodels.WorkActivityDetailsViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Students.route,
        modifier = modifier.padding(12.dp)
    ) {
        animatedComposable(Screen.Students.route) {
            it.GetStudentsListScreen(navController = navController)
        }

        animatedComposable(Screen.Activities.route) {
            it.GetWorkActivitiesListScreen(navController = navController)
        }

        animatedComposable(
            route = Screen.StudentDetails.route,
            arguments = listOf(
                navArgument("STUDENT_ID") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {
            it.GetStudentDetailsScreen(navController = navController)
        }

        animatedComposable(
            route = Screen.ActivityDetails.route,
            arguments = listOf(
                navArgument("ACTIVITY_ID") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {
            it.GetWorkActivityDetailsScreen(navController = navController)
        }

        animatedComposable(Screen.AddStudent.route) {
            it.GetAddStudentScreen(navController = navController)
        }

        animatedComposable(Screen.AddGeneralActivity.route) {
            it.GetAddGeneralActivityScreen(navController = navController)
        }

        animatedComposable(
            route = Screen.AddIndividualActivity.route,
            arguments = listOf(
                navArgument("STUDENT_ID") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {
            it.GetAddIndividualActivityScreen(navController = navController)
        }
    }
}

private fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(durationMillis = 700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(durationMillis = 700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(durationMillis = 700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(durationMillis = 700)
            )
        }
    ) {
        content(it)
    }
}


@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController, route: String): T {
    return navController.findDestination(route)?.let {
        val parentBackStackEntry = remember(this) {
            navController.getBackStackEntry(route)
        }
        viewModel(viewModelStoreOwner = parentBackStackEntry)
    } ?: viewModel()
}


@Composable
private fun NavBackStackEntry.GetStudentsListScreen(navController: NavController) {
    val viewModel = sharedViewModel<StudentsViewModel>(navController = navController, route = Screen.Students.route)

    val navigateToStudentDetailScreen: (Long) -> Unit = {
        navController.navigate("${Screen.StudentDetails.baseRoute}?STUDENT_ID=$it")
    }

    val navigateToAddStudentScreen: () -> Unit = {
        navController.navigate(Screen.AddStudent.baseRoute)
    }

    StudentListScreen(
        viewModel = viewModel,
        navigateToStudentDetailsScreen = navigateToStudentDetailScreen,
        navigateToAddStudentScreen = navigateToAddStudentScreen
    )
}

@Composable
private fun NavBackStackEntry.GetWorkActivitiesListScreen(navController: NavController) {
    val viewModel = sharedViewModel<WorkActivitiesViewModel>(navController = navController, route = Screen.Activities.route)

    val navigateToActivityDetailsScreen: (Long) -> Unit = {
        navController.navigate("${Screen.ActivityDetails.baseRoute}?ACTIVITY_ID=$it")
    }

    val navigateToAddWorkActivityScreen: () -> Unit = {
        navController.navigate(Screen.AddGeneralActivity.baseRoute)
    }

    WorkActivitiesListScreen(
        viewModel = viewModel,
        navigateToActivityDetailsScreen = navigateToActivityDetailsScreen,
        navigateToAddWorkActivityScreen = navigateToAddWorkActivityScreen
    )
}

@Composable
private fun NavBackStackEntry.GetStudentDetailsScreen(navController: NavController) {
    val viewModel = sharedViewModel<StudentDetailsViewModel>(navController = navController, route = Screen.StudentDetails.route)

    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    val studentId = arguments?.getLong("STUDENT_ID")!!

    StudentDetailScreen(
        viewModel = viewModel,
        studentId = studentId,
        navigateBack = navigateBack
    )
}

@Composable
private fun NavBackStackEntry.GetWorkActivityDetailsScreen(navController: NavController) {
    val viewModel = sharedViewModel<WorkActivityDetailsViewModel>(navController = navController, route = Screen.ActivityDetails.route)

    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    val activityId = arguments?.getLong("ACTIVITY_ID")!!

    WorkActivityDetailScreen(
        viewModel = viewModel,
        activityId = activityId,
        navigateBack = navigateBack
    )
}

@Composable
private fun NavBackStackEntry.GetAddStudentScreen(navController: NavController) {
    val viewModel = sharedViewModel<StudentsViewModel>(navController = navController, route = Screen.Students.route)

    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    AddStudentScreen(
        viewModel = viewModel,
        navigateBack = navigateBack
    )
}

@Composable
private fun NavBackStackEntry.GetAddGeneralActivityScreen(navController: NavController) {
    val viewModel = sharedViewModel<WorkActivitiesViewModel>(navController = navController, route = Screen.Activities.route)

    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    AddWorkActivityScreen(
        viewModel = viewModel,
        navigateBack = navigateBack
    )
}

@Composable
private fun NavBackStackEntry.GetAddIndividualActivityScreen(navController: NavController) {
    val viewModel = sharedViewModel<WorkActivitiesViewModel>(navController = navController, route = Screen.Activities.route)

    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    val studentId = arguments?.getLong("STUDENT_ID")!!

    AddWorkActivityScreen(
        viewModel = viewModel,
        navigateBack = navigateBack,
        isToAddIndividualActivity = true,
        activityStudentId = studentId
    )
}