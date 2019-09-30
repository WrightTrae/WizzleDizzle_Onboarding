package com.example.wizzledizzle_onboarding;

import android.graphics.Color
import android.graphics.drawable.*
import androidx.annotation.ColorInt

//
// Created by TDCW on 2019-09-29.
class Utils {

    companion object {
        fun changeBackground(d: Drawable, color: Int): Drawable {
            return d.overrideColor(color)
        }

        @ColorInt fun darkenColor(@ColorInt color: Int): Int {
            return Color.HSVToColor(FloatArray(3).apply {
                Color.colorToHSV(color, this)
                this[2] *= 0.8f
            })
        }

        private fun Drawable.overrideColor(@ColorInt colorInt: Int): Drawable {
            if (this is StateListDrawable){
                val stateList: StateListDrawable = this
                val drawableContainerState = stateList.constantState as DrawableContainer.DrawableContainerState
                val children = drawableContainerState.children
                val selectedItem = children[0] as GradientDrawable
                val focusedItem = children[1] as GradientDrawable
                val unSelectedItem = children[2] as GradientDrawable
                val darkenedColor: Int = darkenColor(colorInt)

                selectedItem.setColor(darkenedColor)
                selectedItem.setStroke(1, darkenedColor)
                focusedItem.setColor(darkenedColor)
                focusedItem.setStroke(1, darkenedColor)
                unSelectedItem.setColor(colorInt)
                unSelectedItem.setStroke(1, colorInt)
            }
            return this
        }
    }
}
