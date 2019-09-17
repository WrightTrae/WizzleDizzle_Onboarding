package com.example.wizzledizzle_onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.view.animation.DecelerateInterpolator
import android.R.attr.start
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class FragmentWelcome : Fragment() {
    val STARTUP_DELAY: Long = 300L
    val ANIM_ITEM_DURATION: Long = 1000L
    val ITEM_DELAY = 300
    val STYLE_EXTRA = "STYLE"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_welcome, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val style: StyleObject
        if (savedInstanceState != null && savedInstanceState.containsKey(STYLE_EXTRA)){
            style = savedInstanceState[STYLE_EXTRA] as StyleObject
        }else{
            style = StyleObject("Test","TTTTTTTEst", null,null,null,null, null)
        }
        animate(view, style)
    }

    private fun animate(view: View, style: StyleObject) {
        val viewAnimations: ArrayList<ViewPropertyAnimatorCompat> = ArrayList()

        // Required Variables
        val titleTV = view.findViewById<TextView>(R.id.welcome_tv_title)
        titleTV.text = style.titleText
        titleTV.translationY = -50f
        viewAnimations.plus(ViewCompat.animate(titleTV)
            .translationY(0f).alpha(1f)
            .setStartDelay((ITEM_DELAY * 0) + 500L)
            .setDuration(1000).setInterpolator(DecelerateInterpolator()))

        val bodyTV = view.findViewById<TextView>(R.id.welcome_tv_body)
        bodyTV.text = style.bodyText
        bodyTV.translationY = -50f
        viewAnimations.plus(ViewCompat.animate(bodyTV)
            .translationY(0f).alpha(1f)
            .setStartDelay((ITEM_DELAY * 1) + 500L)
            .setDuration(1000).setInterpolator(DecelerateInterpolator()))

        val continueBtn = view.findViewById<Button>(R.id.welcome_btn_continue)
        continueBtn.text = style.buttonText ?: "Continue"
        continueBtn.setOnClickListener{
            animate(view, style)
        }
        // TODO button color continueBtn.background
        viewAnimations.plus(ViewCompat.animate(continueBtn)
            .scaleY(1F).scaleX(1f)
            .setStartDelay(ITEM_DELAY * 2 + 500L)
            .setDuration(500L).setInterpolator(DecelerateInterpolator()))


        val logoImageView = view.findViewById<ImageView>(R.id.welcome_iv_logo)
        logoImageView.translationY = 300f
        viewAnimations.plus(ViewCompat.animate(logoImageView)
            .translationY(0f)
            .setStartDelay(STARTUP_DELAY)
            .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                DecelerateInterpolator(1.2f)
            ))
        // Optional Variables
//        style.icon?.let {
//            val logoImageView = view.findViewById<ImageView>(R.id.welcome_iv_logo)
//            logoImageView.setImageBitmap(it)
//            viewAnimations.plus(ViewCompat.animate(logoImageView)
//                .translationY(-300f)
//                .setStartDelay(STARTUP_DELAY).alpha(1f)
//                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
//                    DecelerateInterpolator(1.2f)
//                ))
//        }

        viewAnimations.forEach {
            it.start()
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): FragmentWelcome {
            val fragment = FragmentWelcome()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}