package com.example.efficientme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotesPage(
    navigateToScheduler: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    tasksModel: TasksViewModel
) {
    var dialogWindow by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopHeader(navigateToScheduler = navigateToScheduler,
                navigateToSearch = navigateToSearch,
                navigateToProfile = navigateToProfile)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF21D6D3), Color(0xFF044FAB)),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        )
                    )
            ) {
                val fabScale by animateFloatAsState(
                    targetValue = if (dialogWindow) 1.2f else 1f,
                    animationSpec = tween(durationMillis = 300)
                )

                FloatingActionButton(
                    onClick = { dialogWindow = true },
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(75.dp * fabScale)
                        .zIndex(1f),
                    containerColor = Color(0xFF87CEEB)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.White,
                        contentDescription = "Add Tasks",
                        modifier = Modifier.size(50.dp)
                    )
                }



                if(dialogWindow) {
                    val date = Calendar.getInstance().time
                    val task = Task(
                        content = "",
                        deadline = date.time,
                        priority = 5,
                        estimatedTime = ""
                    )
                    BasicAlertDialog(onDismissRequest = { dialogWindow = false }
                    ) {
                        AddNotesPopUpWindow(task = task,
                            dismissRequest = { dialogWindow = false },
                            onSave = { task -> tasksModel.insertTasks(task) }
                        )
                    }
                } else {
                        DisplayTasks(tasksModel = tasksModel)
                }
            }
        }
    }
}

@Composable
fun TopHeader(navigateToScheduler: () -> Unit, navigateToSearch: () -> Unit, navigateToProfile: () -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "EfficientMe",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF0288D1),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Start
                    ),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.dancing_font)),
                    modifier = Modifier.weight(1f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "First Action",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                navigateToSearch()
                            }
                    )

                    Image(
                        painter = painterResource(R.drawable.scheduler_icon_image),
                        contentDescription = "Second Action",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                navigateToScheduler()
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Third Action",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                navigateToProfile()
                            }
                    )
                }
            }
        }
    }
}