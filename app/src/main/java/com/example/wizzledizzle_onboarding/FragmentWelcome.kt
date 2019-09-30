package com.example.wizzledizzle_onboarding

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.io.ByteArrayOutputStream




class FragmentWelcome : Fragment() {
    private val STARTUP_DELAY: Long = 300L
    private val ANIM_ITEM_DURATION: Long = 1000L
    private val ITEM_DELAY = 300
    private var icon: Bitmap? = null
    private var titleText = ""
    private var bodyText = ""
    private var buttonText = "Continue"
    private var continueButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the screen details that were passed in
        arguments?.getByteArray(ICON_EXTRA).let {
            val byteArray: ByteArray? = it
            if(byteArray != null) {
                icon = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
        }
        arguments?.getString(TITLE_EXTRA).let {
            if(it != null){
                titleText = it
            }else{
                print("Title text appears to be null!!")
            }
        }
        arguments?.getString(BODY_EXTRA).let {
            if(it != null){
                bodyText = it
            }else{
                print("Body text appears to be null!!")
            }
        }
        arguments?.getString(BUTTON_EXTRA).let {
            if(it != null){
                buttonText = it
            }else{
                print("Button text appears to be null!!")
            }
        }
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val style: StyleObject
        arguments?.getSerializable(STYLE_EXTRA).let {
            style = it as StyleObject
        }
        view.setBackgroundColor(style.backgroundColor)

        val continueBtn = view.findViewById<Button>(R.id.welcome_btn_continue)
        continueBtn.setOnClickListener{
            animate(view, style)
        }

        animate(view, style)
    }

    private fun animate(view: View, style: StyleObject) {
        val viewAnimations: ArrayList<ViewPropertyAnimatorCompat> = ArrayList()

        // Required Variables
        val titleTV = view.findViewById<TextView>(R.id.welcome_tv_title)
        titleTV.text = titleText
        titleTV.setTextColor(style.textColor)
        titleTV.translationY = -50f
        viewAnimations.plus(ViewCompat.animate(titleTV)
            .translationY(0f).alpha(1f)
            .setStartDelay((ITEM_DELAY * 0) + 500L)
            .setDuration(1000).setInterpolator(DecelerateInterpolator()))

        val bodyTV = view.findViewById<TextView>(R.id.welcome_tv_body)
        bodyTV.text = bodyText
        bodyTV.setTextColor(style.textColor)
        bodyTV.translationY = -50f
        viewAnimations.plus(ViewCompat.animate(bodyTV)
            .translationY(0f).alpha(1f)
            .setStartDelay((ITEM_DELAY * 1) + 500L)
            .setDuration(1000).setInterpolator(DecelerateInterpolator()))

        if(continueButton == null){
            continueButton = view.findViewById(R.id.welcome_btn_continue)
        }
        continueButton!!.text = buttonText
        continueButton!!.setBackgroundColor(style.buttonColor)
        continueButton!!.setTextColor(style.buttonTextColor)
        viewAnimations.plus(ViewCompat.animate(continueButton!!)
            .scaleY(1F).scaleX(1f)
            .setStartDelay(ITEM_DELAY * 2 + 500L)
            .setDuration(500L).setInterpolator(DecelerateInterpolator()))

        // Optional Variables
        icon?.let {
            val logoImageView = view.findViewById<ImageView>(R.id.welcome_iv_logo)
            logoImageView.setImageBitmap(it)
            logoImageView.translationY = 300f
            viewAnimations.plus(ViewCompat.animate(logoImageView)
                .translationY(0f)
                .setStartDelay(STARTUP_DELAY).alpha(1f)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                    DecelerateInterpolator(1.2f)
                ))
        }

        viewAnimations.forEach {
            it.start()
        }
    }

    companion object {
        private const val TITLE_EXTRA = "TITLE_EXTRA"
        private const val BODY_EXTRA = "BODY_EXTRA"
        private const val BUTTON_EXTRA = "BUTTON_EXTRA"
        private const val ICON_EXTRA = "ICON_EXTRA"
        private const val STYLE_EXTRA = "STYLE_EXTRA"


        fun newInstance(titleText: String, bodyText: String, buttonText: String, icon: Bitmap?, style: StyleObject): FragmentWelcome {
            val fragment = FragmentWelcome()
            val args = Bundle()
            args.putString(TITLE_EXTRA, titleText)
            args.putString(BODY_EXTRA, bodyText)
            args.putString(BUTTON_EXTRA, buttonText)
            icon?.let {
                // Convert Bitmap to byte array so it can be serializable
                val stream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                args.putByteArray(ICON_EXTRA, byteArray)
            }
            args.putSerializable(STYLE_EXTRA, style)
            fragment.arguments = args
            return fragment
        }
    }
}