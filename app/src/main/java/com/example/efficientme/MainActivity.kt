package com.example.efficientme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.efficientme.ui.theme.EfficientMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database: AppDatabase = AppDatabase.getDatabase(application)

            val tasksRepository = TasksRepository(tasksDao = database.tasksDao())
            val tasksModel: TasksViewModel = viewModel(factory = TasksViewModelFactory(tasksRepository))

            val profileRepository = ProfileRepository(profileDao = database.profileDao())
            val profileModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(profileRepository))

            val breaksRepository = BreaksRepository(breaksDao = database.breaksDao())
            val breaksModel: BreaksViewModel = viewModel(factory = BreaksViewModelFactory(breaksRepository))

            EfficientMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EfficientMe(tasksModel = tasksModel,
                        profileModel = profileModel,
                        breaksModel = breaksModel)
                }
            }
        }
    }
}
