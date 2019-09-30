package com.example.wizzledizzle_onboarding

import android.graphics.Bitmap
import android.graphics.Color
import java.io.Serializable

class StyleObject (
    textColorString: String,
    backgroundColorString: String,
    buttonColorString: String,
    buttonTextColorString: String
): Serializable {
    var textColor = Color.parseColor(textColorString)
    var backgroundColor = Color.parseColor(backgroundColorString)
    var buttonColor = Color.parseColor(buttonColorString)
    var buttonTextColor = Color.parseColor(buttonTextColorString)
}