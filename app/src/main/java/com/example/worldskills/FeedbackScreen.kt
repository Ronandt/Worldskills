package com.example.worldskills

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.worldskills.ui.theme.appBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navigateBack: () -> Unit) {
    Scaffold(topBar = {

        TopAppBar(title = { Text(text = "Feedback")}, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = appBarColor), navigationIcon = {
            IconButton(onClick = navigateBack) {
                androidx.compose.material3.Icon(Icons.Default.ArrowBack, contentDescription = "Go back ")
            }
        })
    }) {
        Box(modifier = Modifier.padding(it)) {
            AndroidView(factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    loadUrl("https://forms.gle/BMdujG1mz9Zgjj716")
                }

            }, modifier = Modifier.fillMaxSize())
        }
    }


}