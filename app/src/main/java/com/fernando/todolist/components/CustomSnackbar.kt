package com.fernando.todolist.components

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.fernando.todolist.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

class CustomSnackbar {
    private var image : ImageButton? = null
    private var textView : TextView? = null

    inner class Build() {
        private var view : View? = null
        private var snackbar : Snackbar? = null
         private var icon : Int? = null
        @ColorInt private var bgColor : Int? = null
        private var text : String? = null

        fun setView(appCompatActivity: AppCompatActivity): Build {
            view = appCompatActivity.layoutInflater.inflate(R.layout.layout_snackbar_sucess, null)
            return this
        }



        fun setIcon(icon: Int): Build {
            this.icon = icon
            return this
        }

        fun setBackgroundColor(@ColorInt bgColor: Int): Build {
            this.bgColor = bgColor
            return this
        }

        fun setText(text: String): Build {
            this.text = text
            return this
        }



        @SuppressLint("ResourceType")
        fun build(view: View) : Snackbar {
            image = this.view?.findViewById(R.id.icon)
            textView = this.view?.findViewById(R.id.snackbar_text)

            icon?.let { image?.setImageResource(it) }
            textView?.text = text



            this.snackbar = text?.let { Snackbar.make(view, it, Snackbar.LENGTH_LONG) }
            this.snackbar?.view?.setBackgroundResource(Color.TRANSPARENT)
            val snackbarLayout = this.snackbar?.view as SnackbarLayout

            bgColor?.let { snackbarLayout.setBackgroundColor(it) }

            snackbarLayout.addView(this.view)
            return this.snackbar!!

        }


    }
}


