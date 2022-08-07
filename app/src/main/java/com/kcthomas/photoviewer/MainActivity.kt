package com.kcthomas.photoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kcthomas.core.compose.theme.PhotoViewerTheme
import com.kcthomas.photoviewer.ui.photolist.PhotoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoViewerTheme {
                PhotoListScreen()
            }
        }
    }
}
