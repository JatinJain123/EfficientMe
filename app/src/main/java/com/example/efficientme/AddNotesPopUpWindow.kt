package com.example.efficientme

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.round

fun convertTimeStampToString(timeStamp: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val date = Date(timeStamp)
    return formatter.format(date)
}

@Composable
fun AddNotesPopUpWindow(task: Task,
                        dismissRequest: () -> Unit,
                        onSave: (Task) -> Unit
) {
    val context = LocalContext.current

    var content by remember { mutableStateOf(task.content) }
    var estimatedTime by remember { mutableStateOf(task.estimatedTime) }
    var priority by remember { mutableIntStateOf(task.priority) }

    val date = convertTimeStampToString(task.deadline).split(" ")
    var deadlineDay by remember { mutableStateOf(date[0]) }
    var deadlineTime by remember { mutableStateOf(date[1]) }
    var deadline by remember { mutableLongStateOf(0L) }

    val calendar = Calendar.getInstance()

    val openDatePickerDialog = {
        val datePickerDialog = DatePickerDialog(context,
            { _, year, month, dayOfMonth ->
                deadlineDay = "$year-${month + 1}-$dayOfMonth"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    val openTimePickerDialog = {
        val timePickerDialog = TimePickerDialog(context,
            { _, hourOfDay, minute ->
                deadlineTime = "$hourOfDay:$minute"
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    // Combine Date and Time
    LaunchedEffect(deadlineDay, deadlineTime) {
        if (deadlineDay.isNotEmpty() && deadlineTime.isNotEmpty()) {
            val fullDateTimeString = "$deadlineDay $deadlineTime"
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val deadlineDate = formatter.parse(fullDateTimeString)
            deadline = deadlineDate?.time ?: 0L
        }
    }

    Box(modifier = Modifier
        .padding(16.dp)
        .wrapContentSize()
        .clip(RoundedCornerShape(16.dp))
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF4A90E2), // Light blue at the center
                        Color(0xFF6581A3)  // Darker blue at the edges
                    ),
                    center = center,
                    radius = size.minDimension / 1.2f
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
        ) {
            // content text field
            OutlinedTextField(value = content,
                onValueChange = {content = it},
                label = { Text(text = "enter task", fontSize = 20.sp, color = Color.Black) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .height(70.dp)
                    .width(300.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Deadline
            Row(horizontalArrangement = Arrangement.Center){
                Text(
                    text = "Deadline",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.mynerve_font)),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // deadline date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Date",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        openDatePickerDialog()
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(value = deadlineDay,
                    onValueChange = { },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                    ),
                    textStyle = TextStyle(Color.Black),
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .padding(0.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // deadline time
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Time",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(2.dp))

                Image(
                    painter = painterResource(R.drawable.normal_clock_image),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clickable {
                        openTimePickerDialog()
                    }
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(value = deadlineTime,
                    onValueChange = { },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                    ),
                    textStyle = TextStyle(Color.Black),
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .padding(0.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // priority line
            Text(
                text = "Priority",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.mynerve_font)),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                for (i in 1..10) {
                    Text(
                        text = "$i",
                        color = if (i == priority) Color.Cyan else Color(0xFFDCDCDC),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clickable { priority = i }
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // estimated Time
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = estimatedTime,
                    onValueChange = { estimatedTime = it },
                    label = { Text(text = "expected time to finish", fontSize = 14.sp, color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                    ),
                    textStyle = TextStyle(Color.Black),
                    modifier = Modifier
                        .height(60.dp)
                        .width(200.dp)
                        .padding(0.dp)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Hrs",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.mynerve_font)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // button
            Button(onClick = {
                val currentTime = Calendar.getInstance().timeInMillis
                when {
                    content.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Content cannot be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    deadline <= currentTime -> {
                        Toast.makeText(
                            context,
                            "Deadline must be in the future",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    estimatedTime.isBlank() || estimatedTime.toIntOrNull() == null -> {
                        Toast.makeText(
                            context,
                            "Please enter a valid estimated time",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    else -> {
                        val taskToInsert = Task(
                            taskId = task.taskId,
                            content = content,
                            estimatedTime = estimatedTime,
                            priority = priority,
                            deadline = deadline
                        )
                        onSave(taskToInsert)
                        Toast.makeText(
                            context,
                            "Task saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismissRequest()
                    }
                }
            },
                colors = ButtonColors(containerColor = Color(0xFF87CEEB),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color(0xFF87CEEB))
            ) {
                Text("Save")
            }
        }
    }
}