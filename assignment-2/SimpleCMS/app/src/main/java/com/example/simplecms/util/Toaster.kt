package com.example.simplecms.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Toaster(private val context: Context) {

    @Suppress("OPT_IN_USAGE")
    fun toast(message: String) {
        // 귀찮아서...
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}