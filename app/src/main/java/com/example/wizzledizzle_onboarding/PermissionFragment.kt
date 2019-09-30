package com.example.wizzledizzle_onboarding

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import java.io.ByteArrayOutputStream


class PermissionFragment : Fragment() {
    private val STARTUP_DELAY: Long = 300L
    private val ANIM_ITEM_DURATION: Long = 1000L
    private val ITEM_DELAY = 300
    private var icon: Bitmap? = null
    private var titleText = ""
    private var bodyText = ""
    private var buttonText = "Continue"
    private var titleTV: TextView? = null
    private var bodyTV: TextView? = null
    private var iconImageView: ImageView? = null
    private var continueButton: Button? = null
    private var style: StyleObject? = null

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
        arguments?.getSerializable(STYLE_EXTRA).let {
            style = it as StyleObject
        }
        view.setBackgroundColor(style!!.backgroundColor)

        // Get Views
        titleTV = view.findViewById(R.id.welcome_tv_title)
        bodyTV = view.findViewById(R.id.welcome_tv_body)
        continueButton = view.findViewById(R.id.welcome_btn_continue)
        iconImageView = view.findViewById(R.id.welcome_iv_logo)

        continueButton!!.setOnClickListener{
            animate(view, style!!)
        }
        if(userVisibleHint) {
            animate(view, style!!)
        }else{
            initViews()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser && view != null && style != null){
            animate(view!!, style!!)
        }else if (!isVisibleToUser && view != null){
            initViews()
        }
    }

    private fun initViews(){
        // Initialize positions
        titleTV!!.alpha = 0f
        titleTV!!.translationY = -50f
        bodyTV!!.alpha = 0f
        bodyTV!!.translationY = -50f
        continueButton!!.scaleX = 0f
        continueButton!!.scaleY = 0f
        iconImageView!!.translationY = 300f
        icon?.let {
            iconImageView!!.setImageBitmap(it)
        }
//        iconImageView!!.visibility = View.INVISIBLE
    }

    private fun animate(view: View, style: StyleObject) {
        // Get views just in case any of them are null
        if(titleTV == null) {
            titleTV = view.findViewById(R.id.welcome_tv_title)
        }
        if(bodyTV == null) {
            bodyTV = view.findViewById(R.id.welcome_tv_body)
        }
        if(continueButton == null){
            continueButton = view.findViewById(R.id.welcome_btn_continue)
        }
        if(iconImageView == null) {
            iconImageView = view.findViewById(R.id.welcome_iv_logo)
        }

        initViews()

        // Animate views
        val viewAnimations: ArrayList<ViewPropertyAnimatorCompat> = ArrayList()

        // Required Variables
        titleTV!!.text = titleText
        titleTV!!.setTextColor(style.textColor)
        viewAnimations.plus(
            ViewCompat.animate(titleTV!!)
                .translationY(0f).alpha(1f)
                .setStartDelay((ITEM_DELAY * 0) + 500L)
                .setDuration(1000).setInterpolator(DecelerateInterpolator()))

        bodyTV!!.text = bodyText
        bodyTV!!.setTextColor(style.textColor)
        viewAnimations.plus(
            ViewCompat.animate(bodyTV!!)
                .translationY(0f).alpha(1f)
                .setStartDelay((ITEM_DELAY * 1) + 500L)
                .setDuration(1000).setInterpolator(DecelerateInterpolator()))

        continueButton!!.text = buttonText
        continueButton!!.setBackgroundColor(style.buttonColor)
        continueButton!!.setTextColor(style.buttonTextColor)
        viewAnimations.plus(
            ViewCompat.animate(continueButton!!)
                .scaleY(1F).scaleX(1f)
                .setStartDelay(ITEM_DELAY * 2 + 500L)
                .setDuration(500L).setInterpolator(DecelerateInterpolator()))

        // Optional Variables
        icon?.let {
            viewAnimations.plus(
                ViewCompat.animate(iconImageView!!)
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


        fun newInstance(titleText: String, bodyText: String, buttonText: String, icon: Bitmap?, style: StyleObject): PermissionFragment {
            val fragment = PermissionFragment()
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