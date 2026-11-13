package com.ujjwal.aivideomaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UjjwalApp()
        }
    }
}

@Composable
fun UjjwalApp() {
    var prompt by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("Text to Video") }
    var message by remember { mutableStateOf("अपना वीडियो बनाने का आइडिया लिखें।") }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "UJJWAL AI",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "AI Video Maker",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text("वीडियो बनाने का तरीका चुनें")

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        mode = "Text to Video"
                    }) {
                        Text("Text to Video")
                    }

                    Button(onClick = {
                        mode = "Photo to Video"
                    }) {
                        Text("Photo to Video")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("चुना गया मोड: $mode")

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text("वीडियो के बारे में लिखें") },
                    placeholder = {
                        Text("जैसे: एक लड़का पहाड़ों में दौड़ रहा है")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        message = if (prompt.isBlank()) {
                            "पहले वीडियो का आइडिया लिखें।"
                        } else {
                            "आपका आइडिया तैयार है! AI वीडियो सर्वर जोड़ना अभी बाकी है।"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("वीडियो बनाएं")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text("UJJWAL AI Video Maker © 2026")
            }
        }
    }
}
