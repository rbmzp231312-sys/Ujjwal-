package com.ujjwal.aivideomaker

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
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
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedPhoto = uri
        message = if (uri != null) {
            "फोटो चुन ली गई है। अगला चरण वीडियो बनाना होगा।"
        } else {
            "कोई फोटो नहीं चुनी गई।"
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("UJJWAL AI", style = MaterialTheme.typography.headlineLarge)
                Text("AI Video Maker", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(30.dp))

                Text("वीडियो बनाने का तरीका चुनें")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        mode = "Text to Video"
                        message = "अपना वीडियो बनाने का आइडिया लिखें।"
                    }) { Text("Text to Video") }

                    Button(onClick = {
                        mode = "Photo to Video"
                        message = "अपनी गैलरी से एक फोटो चुनें।"
                    }) { Text("Photo to Video") }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text("चुना गया मोड: $mode")
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { photoPicker.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (selectedPhoto == null) "📷 गैलरी से फोटो चुनें" else "📷 दूसरी फोटो चुनें")
                }
                Text(
                    if (selectedPhoto == null) "फोटो से वीडियो बनाने के लिए यहाँ दबाकर फोटो चुनें।"
                    else "फोटो चुन ली गई है ✓",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text("वीडियो के बारे में लिखें") },
                    placeholder = { Text("जैसे: एक लड़का पहाड़ों में दौड़ रहा है") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        message = when {
                            prompt.isBlank() -> "पहले वीडियो का आइडिया लिखें।"
                            mode == "Photo to Video" && selectedPhoto == null ->
                                "पहले गैलरी से एक फोटो चुनें।"
                            else ->
                                "तुम्हारा आइडिया और फोटो तैयार हैं। असली MP4/AI वीडियो बनाने वाला इंजन अभी जोड़ना बाकी है; यह बटन अभी वीडियो एक्सपोर्ट नहीं करता।"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("वीडियो बनाएं") }

                Spacer(modifier = Modifier.height(20.dp))
                Text(message, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(30.dp))
                Text("UJJWAL AI Video Maker © 2026")
            }
        }
    }
}
