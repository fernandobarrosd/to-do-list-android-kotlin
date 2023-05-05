package com.fernando.todolist.components;

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fernando.todolist.R
import com.fernando.todolist.utils.getInflater
import com.fernando.todolist.utils.getTypedArray

class CustomAlertDialog(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var btnYes: Button? = null
    private var btnNo: Button? = null
    private var dialogTitle: TextView? = null
    private val typedArray = getTypedArray(context, attrs, R.styleable.CustomAlertDialog)


    init {
        init()
        dialogTitle?.text = typedArray.getString(R.styleable.CustomAlertDialog_dialog_title)
        btnYes?.text = typedArray.getString(R.styleable.CustomAlertDialog_button1_text)
        btnNo?.text = typedArray.getString(R.styleable.CustomAlertDialog_button2_text)
        btnYes?.id = typedArray.getResourceId(R.styleable.CustomAlertDialog_firstButtonID, 0)
        btnNo?.id = typedArray.getResourceId(R.styleable.CustomAlertDialog_secondButtonID, 0)
    }

    private fun init() {
        val inflater = getInflater(context)
        inflater.inflate(R.layout.layout_custom_dialog, this)

        btnYes = findViewById(R.id.btnYes)
        btnNo = findViewById(R.id.btnNo)
        dialogTitle = findViewById(R.id.dialogTitle)
    }
}