package com.techullurgy.tuitiontracker.android.screens

sealed class Screen(val baseRoute: String, val route: String) {
    data object Students: Screen(baseRoute = "Students", route = "Students")
    data object Activities: Screen(baseRoute = "Activities", route = "Activities")
    data object StudentDetails: Screen(baseRoute = "StudentDetails", route = "StudentDetails?STUDENT_ID={STUDENT_ID}")
    data object ActivityDetails: Screen(baseRoute = "ActivityDetails", route = "ActivityDetails?ACTIVITY_ID={ACTIVITY_ID}")
    data object AddGeneralActivity: Screen(baseRoute = "AddGeneralActivity", route = "AddGeneralActivity")
    data object AddIndividualActivity: Screen(baseRoute = "AddIndividualActivity", route = "AddIndividualActivity?STUDENT_ID={STUDENT_ID}")
    data object AddStudent: Screen(baseRoute = "AddStudent", route = "AddStudent")
}
