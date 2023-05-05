package com.fernando.todolist.classes

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity

abstract class CustomActivity : AppCompatActivity() {
    fun <T> navigateTo(screen: Class<T>) {
        val intent = Intent(this, screen)
        this.startActivity(intent)

    }
    fun <T, Data> navigateTo(screen: Class<T>, dataKey: String, mapData: ArrayList<Data>) {
        intent = Intent(this, screen);

        intent.putExtra(dataKey, mapData)

        startActivity(intent)

    }
}




