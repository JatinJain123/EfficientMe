package com.example.efficientme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun EfficientMe(
    tasksModel: TasksViewModel,
    profileModel: ProfileViewModel,
    breaksModel: BreaksViewModel
) {
    var showAnimation by remember { mutableStateOf(true) }

    if(showAnimation) {
        Animate {
            showAnimation = false
        }
    } else {
        Navigation(tasksModel = tasksModel,
            profileModel = profileModel,
            breaksModel = breaksModel)
    }
}

@Composable
fun Navigation(
    tasksModel: TasksViewModel,
    profileModel: ProfileViewModel,
    breaksModel: BreaksViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "addNotesPage"
    ) {
        // notes Adding screen
        composable(
            route = "addNotesPage"
        ) {
            AddNotesPage(
                navigateToScheduler = { navController.navigate("SchedulerPage") },
                navigateToSearch = { navController.navigate("SearchPage") },
                navigateToProfile = { navController.navigate("ProfilePage") },
                tasksModel = tasksModel
            )
        }

        // Scheduler screen
        composable(
            route = "SchedulerPage"
        ) {
            SchedulerPage(
                tasksModel = tasksModel,
                profileModel = profileModel,
                breaksModel = breaksModel
            ) {
                navController.popBackStack()
            }
        }

        // searching screen
        composable(
            route = "SearchPage"
        ) {
            SearchPage {
                navController.popBackStack()
            }
        }

        // user profile screen
        composable(
            route = "ProfilePage"
        ) {
            ProfilePage(
                profileModel = profileModel,
                breaksModel = breaksModel
            ) {
                navController.popBackStack()
            }
        }
    }
}