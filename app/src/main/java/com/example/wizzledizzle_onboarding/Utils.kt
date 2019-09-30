package com.example.wizzledizzle_onboarding;

import android.graphics.Color
import android.graphics.drawable.*
import androidx.annotation.ColorInt

//
// Created by TDCW on 2019-09-29.
class Utils {

    companion object {
        private val whiteColorInt: Int = Color.parseColor("#FFFFFF")

        fun changeBackground(d: Drawable, color: Int, borderOnly: Boolean): Drawable {
            return d.overrideColor(color, borderOnly)
        }

        @ColorInt fun darkenColor(@ColorInt color: Int): Int {
            return Color.HSVToColor(FloatArray(3).apply {
                Color.colorToHSV(color, this)
                this[2] *= 0.8f
            })
        }

        private fun Drawable.overrideColor(@ColorInt colorInt: Int, borderOnly: Boolean): Drawable {
            if (this is StateListDrawable){
                val stateList: StateListDrawable = this
                val drawableContainerState = stateList.constantState as DrawableContainer.DrawableContainerState
                val children = drawableContainerState.children
                val selectedItem = children[0] as GradientDrawable
                val focusedItem = children[1] as GradientDrawable
                val unSelectedItem = children[2] as GradientDrawable
                val darkenedColor: Int = darkenColor(colorInt)
                selectedItem.setStroke(3, darkenedColor)
                focusedItem.setStroke(3, darkenedColor)
                unSelectedItem.setStroke(3, colorInt)
                if(!borderOnly){
                    unSelectedItem.setColor(colorInt)
                    focusedItem.setColor(darkenedColor)
                    selectedItem.setColor(darkenedColor)
                }else{
                    val darkenedWhite: Int = darkenColor(whiteColorInt)
                    unSelectedItem.setColor(whiteColorInt)
                    focusedItem.setColor(darkenedWhite)
                    selectedItem.setColor(darkenedWhite)
                }
            }
            return this
        }
    }
}
