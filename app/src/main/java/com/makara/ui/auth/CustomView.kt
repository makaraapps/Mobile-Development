package com.makara.ui.auth

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.makara.R

class CustomView : TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val parentLayout = parent.parent as? TextInputLayout
                when (inputType) {
                    EMAIL -> {
                        if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                            setError(context.getString(R.string.email_error), null)
                            parentLayout?.boxStrokeColor = Color.RED
                            parentLayout?.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
                        } else {
                            parentLayout?.boxStrokeColor = ContextCompat.getColor(context, R.color.brown)
                            parentLayout?.defaultHintTextColor = ColorStateList.valueOf(
                                ContextCompat.getColor(context, R.color.brown))
                        }
                    }
                    PASSWORD -> {
                        if (s.toString().length < 8) {
                            setError(context.getString(R.string.password_error), null)
                            parentLayout?.boxStrokeColor = Color.RED
                            parentLayout?.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
                        } else {
                            parentLayout?.boxStrokeColor = ContextCompat.getColor(context, R.color.brown)
                            parentLayout?.defaultHintTextColor = ColorStateList.valueOf(
                                ContextCompat.getColor(context, R.color.brown))
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    companion object {
        const val EMAIL = 0x00000021
        const val PASSWORD = 0x00000081
    }
}