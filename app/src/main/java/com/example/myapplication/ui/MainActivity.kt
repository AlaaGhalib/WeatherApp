package com.example.myapplication.ui

import MainApp
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.ui.themes.MyApplicationTheme
import com.example.myapplication.utils.NetworkUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!NetworkUtils.isInternetAvailable(this)) {
            Toast.makeText(this, "No internet connection available", Toast.LENGTH_LONG).show()
        }
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}
