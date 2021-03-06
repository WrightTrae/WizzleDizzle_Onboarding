package com.example.wizzledizzle_onboarding

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.fragment.app.Fragment

class WizzleDizzleOnboarding(
    val context: Context
) {
    var fragments: ArrayList<Fragment> = ArrayList()

    var style: StyleObject = StyleObject(
        "#000000",
        "#FFFFFF",
        "#006FBA",
        "#FFFFFF")

    fun addWelcomeScreen(titleText: String, bodyText: String, buttonText: String, icon: Bitmap?){
        fragments.add(FragmentWelcome.newInstance(titleText, bodyText, buttonText, icon, style))
    }

    fun addPermissionScreen(permission: String, titleText: String, bodyText: String, showIcon: Boolean){
        fragments.add(PermissionFragment.newInstance(permission, titleText, bodyText, showIcon, style))
    }
}