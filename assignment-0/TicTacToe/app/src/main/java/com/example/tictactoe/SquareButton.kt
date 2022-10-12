package com.example.tictactoe

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class SquareButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatButton(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // SQUAREEEEEEEEEEEEEE
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}