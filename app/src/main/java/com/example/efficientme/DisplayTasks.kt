package com.example.efficientme

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DisplayTasks(
    tasksModel: TasksViewModel,
) {
    LaunchedEffect(Unit) {
        tasksModel.getTasks()
    }

    val state by tasksModel.status

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.loading -> LoadingView()
            state.error != null -> ErrorView(errorMessage = state.error!!, tasksModel = tasksModel)
            state.data.isNullOrEmpty() -> EmptyView(message = "No tasks added yet!")
            else -> TasksList(tasks = state.data!!, tasksModel = tasksModel)
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading...",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
    }
}

@Composable
fun ErrorView(errorMessage: String, tasksModel: TasksViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Error",
            tint = Color.LightGray,
            modifier = Modifier
                .size(60.dp)
                .clickable {
                   tasksModel.getTasks()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Oops! An error occurred.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            fontSize = 14.sp,
            color = Color.LightGray
        )
    }
}

@Composable
fun EmptyView(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Empty",
            tint = Color.LightGray,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksList(tasks: List<Task>, tasksModel: TasksViewModel) {
    var dialogWindow by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    tasksModel = tasksModel,
                    onEdit = {
                        dialogWindow = true
                        taskToEdit = task
                    }
                )
            }
        }

        if (dialogWindow) {
            BasicAlertDialog(onDismissRequest = {
                dialogWindow = false
                taskToEdit = null
            }) {
                taskToEdit?.let {
                    AddNotesPopUpWindow(
                        task = it,
                        dismissRequest = {
                            dialogWindow = false
                            taskToEdit = null
                        },
                        onSave = { task -> tasksModel.updateTasks(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    tasksModel: TasksViewModel,
    onEdit: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp)), // Slight elevation and rounded corners
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Task Content
            Text(
                text = task.content,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F) // Darker text color
                ),
                fontSize = 20.sp
            )

            // Deadline and Estimated Time
            Text(
                text = "Deadline: ${task.deadline.toHumanReadable()}",
                fontSize = 14.sp,
                color = Color(0xFF78909C)
            )

            Text(
                text = "Estimated Finish Time: ${task.estimatedTime} Hrs",
                fontSize = 14.sp,
                color = Color(0xFF78909C)
            )

            // Priority
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Priority: ${task.priority}",
                    fontSize = 14.sp,
                    color = when (task.priority) {
                        in 1..3 -> Color(0xFF43A047)
                        in 4..6 -> Color(0xFFFFA000)
                        else -> Color(0xFFD32F2F)
                    },
                    fontWeight = FontWeight.Medium
                )

                // Edit and Delete Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Task",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onEdit() }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                tasksModel.deleteTasks(task = task)
                                Toast.makeText(context, "task deleted", Toast.LENGTH_SHORT).show()
                            }
                    )
                }
            }
        }
    }
}

fun Long.toHumanReadable(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

