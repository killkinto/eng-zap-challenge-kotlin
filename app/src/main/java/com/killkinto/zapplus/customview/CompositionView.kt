package com.killkinto.zapplus.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.killkinto.zapplus.R

class CompositionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0,
    defStyleRes: Int = 0
) :
    LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.composition_view, this)

        attrs?.let {
            val imageView: ImageView = findViewById(R.id.image)
            val textView: TextView = findViewById(R.id.text)

            val attributes = context.obtainStyledAttributes(attrs, R.styleable.CompositionView)
            imageView.setImageDrawable(attributes.getDrawable(R.styleable.CompositionView_image))
            textView.text = attributes.getString(R.styleable.CompositionView_text)
            attributes.recycle()
        }
    }
}