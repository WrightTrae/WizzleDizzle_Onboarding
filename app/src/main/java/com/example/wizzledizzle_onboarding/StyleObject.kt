package com.example.wizzledizzle_onboarding

import android.graphics.Bitmap
import android.graphics.Color
import java.io.Serializable

class StyleObject (val titleText: String, val bodyText: String, val icon: Bitmap?, val textColor: Color?, val backgroundColor: Color?, val buttonText: String?, val buttonColor: String?): Serializable {
    init {

    }
}