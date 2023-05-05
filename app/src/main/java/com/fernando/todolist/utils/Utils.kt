package com.fernando.todolist.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StyleableRes

fun getInflater(context: Context) : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
fun getTypedArray(context: Context, attrs: AttributeSet, @StyleableRes styleable : IntArray) = context.obtainStyledAttributes(attrs, styleable)