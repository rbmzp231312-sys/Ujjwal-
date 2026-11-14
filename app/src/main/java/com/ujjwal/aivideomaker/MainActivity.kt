package com.ujjwal.aivideomaker

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
        setContent { UjjwalApp() }
    }
}

@Composable
fun UjjwalApp() {
    var prompt by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("Text to Video") }
    var message by remember { mutableStateOf("अपना वीडियो बनाने का आइडिया लिखें।") }
    var selectedPhoto by remember { mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedPhoto = uri
        message = if (uri != null) {
            "फोटो चुन ली गई है। अगला चरण वीडियो बनाना होगा।"
        } else {
            "फोटो नहीं चुनी गई। फिर से फोटो चुनें।"
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("UJJWAL AI", style = MaterialTheme.typography.headlineLarge)
                Text("AI Video Maker • Version 1.3", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(22.dp))

                Text("वीडियो बनाने का तरीका चुनें")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            mode = "Text to Video"
                            message = "अपना वीडियो बनाने का आइडिया लिखें।"
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Text to Video") }

                    Button(
                        onClick = {
                            mode = "Photo to Video"
                            message = "नीचे बड़े फोटो चुनें बटन को दबाएँ।"
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Photo to Video") }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text("चुना गया मोड: $mode", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(14.dp))

                // Always-visible, high-contrast gallery action.
                Button(
                    onClick = {
                        photoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                ) {
                    Text(
                        if (selectedPhoto == null) "📷  फोटो चुनें — गैलरी खोलें"
                        else "📷  दूसरी फोटो चुनें",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    if (selectedPhoto == null) "ऊपर वाले नीले बटन को दबाकर फोन की फोटो चुनें।"
                    else "फोटो सफलतापूर्वक चुन ली गई है ✓",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(18.dp))
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text("वीडियो के बारे में लिखें") },
                    placeholder = { Text("जैसे: एक लड़का पहाड़ों में दौड़ रहा है") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = {
                        message = when {
                            prompt.isBlank() -> "पहले वीडियो का आइडिया लिखें।"
                            mode == "Photo to Video" && selectedPhoto == null ->
                                "पहले ऊपर फोटो चुनें बटन दबाकर फोटो चुनें।"
                            else ->
                                "तुम्हारा आइडिया और फोटो तैयार हैं। असली AI/MP4 वीडियो इंजन अभी जोड़ना बाकी है; अभी वीडियो एक्सपोर्ट नहीं होता।"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("वीडियो बनाएं") }

                Spacer(modifier = Modifier.height(18.dp))
                Text(message, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(26.dp))
                Text("UJJWAL AI Video Maker © 2026")
            }
        }
    }
}
