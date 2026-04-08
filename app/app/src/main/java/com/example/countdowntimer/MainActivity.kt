package com.example.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownTimerTheme {
                CountdownTimerScreen()
            }
        }
    }
}

@Composable
fun CountdownTimerTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Composable
fun CountdownTimerScreen() {
    var inputSeconds by remember { mutableStateOf("") }
    var remainingSeconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning, remainingSeconds) {
        if (isRunning && remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        } else if (isRunning && remainingSeconds == 0) {
            isRunning = false
            isFinished = true
        }
    }

    val backgroundColor = if (isFinished) Color(0xFFFFCDD2) else Color.White

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Trình Đếm Ngược", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))

            Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(
                    text = formatTime(remainingSeconds),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isFinished) Color.Red else Color(0xFF1976D2),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                )
            }

            if (isFinished) {
                Text("Hết giờ!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = inputSeconds,
                onValueChange = { if (it.isEmpty() || it.all { c -> c.isDigit() }) inputSeconds = it },
                label = { Text("Nhập số giây") },
                enabled = !isRunning,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val seconds = inputSeconds.toIntOrNull() ?: 0
                        if (seconds > 0 && !isRunning) {
                            remainingSeconds = seconds
                            isRunning = true
                            isFinished = false
                        }
                    },
                    enabled = !isRunning && inputSeconds.isNotEmpty(),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("Bắt đầu")
                }

                OutlinedButton(
                    onClick = {
                        remainingSeconds = 0
                        inputSeconds = ""
                        isRunning = false
                        isFinished = false
                    },
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("Reset")
                }
            }

            if (isRunning) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { isRunning = false },
                    modifier = Modifier.fillMaxWidth().height(48.dp).padding(16.dp)
                ) {
                    Text("Dừng")
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}