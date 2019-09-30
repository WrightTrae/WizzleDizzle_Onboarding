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
import java.security.Permission
import java.util.jar.Manifest


class PermissionFragment : Fragment() {
    private val STARTUP_DELAY: Long = 300L
    private val ANIM_ITEM_DURATION: Long = 1000L
    private val ITEM_DELAY = 300
    private var showIcon: Boolean? = null
    private var titleText = ""
    private var bodyText = ""
    private var titleTV: TextView? = null
    private var bodyTV: TextView? = null
    private var iconImageView: ImageView? = null
    private var allowButton: Button? = null
    private var denyButton: Button? = null
    private var style: StyleObject? = null
    private var permission: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the screen details that were passed in
        arguments?.getBoolean(SHOW_ICON_EXTRA).let {
            if(it != null){
                showIcon = it
            }else{
                print("Show icon appears to be null!!")
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
        arguments?.getString(PERMISSION_EXTRA).let {
            if(it != null){
                permission = it
            }else{
                print("Permission appears to be null!!")
            }
        }
        return inflater.inflate(R.layout.permission_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getSerializable(STYLE_EXTRA).let {
            style = it as StyleObject
        }
        view.setBackgroundColor(style!!.backgroundColor)

        // Get Views
        titleTV = view.findViewById(R.id.permission_tv_title)
        bodyTV = view.findViewById(R.id.permission_tv_body)
        allowButton = view.findViewById(R.id.permission_btn_allow)
        denyButton = view.findViewById(R.id.permission_btn_deny)
        iconImageView = view.findViewById(R.id.permission_iv_icon)

        allowButton!!.setOnClickListener{
            animate(view, style!!)
        }
        denyButton!!.setOnClickListener{
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
        allowButton!!.scaleX = 0f
        allowButton!!.scaleY = 0f
        denyButton!!.scaleX = 0f
        denyButton!!.scaleY = 0f
        iconImageView!!.translationY = 300f
//        iconImageView!!.visibility = View.INVISIBLE
    }

    private fun animate(view: View, style: StyleObject) {
        // Get views just in case any of them are null
        if(titleTV == null) {
            titleTV = view.findViewById(R.id.permission_tv_title)
        }
        if(bodyTV == null) {
            bodyTV = view.findViewById(R.id.permission_tv_body)
        }
        if(allowButton == null){
            allowButton = view.findViewById(R.id.permission_btn_allow)
        }
        if(denyButton == null){
            denyButton = view.findViewById(R.id.permission_btn_deny)
        }
        if(iconImageView == null) {
            iconImageView = view.findViewById(R.id.permission_iv_icon)
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

        allowButton!!.background = Utils.changeBackground(allowButton!!.background, style.buttonColor, false)
        allowButton!!.setTextColor(style.buttonTextColor)
        viewAnimations.plus(
            ViewCompat.animate(allowButton!!)
                .scaleY(1F).scaleX(1f)
                .setStartDelay(ITEM_DELAY * 2 + 500L)
                .setDuration(500L).setInterpolator(DecelerateInterpolator()))

        denyButton!!.background = Utils.changeBackground(denyButton!!.background, style.buttonColor, true)
        denyButton!!.setTextColor(style.buttonColor)
        viewAnimations.plus(
            ViewCompat.animate(denyButton!!)
                .scaleY(1F).scaleX(1f)
                .setStartDelay(ITEM_DELAY * 2 + 500L)
                .setDuration(500L).setInterpolator(DecelerateInterpolator()))

        // Optional Variables

        viewAnimations.plus(
            ViewCompat.animate(iconImageView!!)
                .translationY(0f)
                .setStartDelay(STARTUP_DELAY).alpha(1f)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                    DecelerateInterpolator(1.2f)
                ))


        viewAnimations.forEach {
            it.start()
        }
    }

    companion object {
        private const val PERMISSION_EXTRA = "PERMISSION_EXTRA"
        private const val TITLE_EXTRA = "TITLE_EXTRA"
        private const val BODY_EXTRA = "BODY_EXTRA"
        private const val SHOW_ICON_EXTRA = "SHOW_ICON_EXTRA"
        private const val STYLE_EXTRA = "STYLE_EXTRA"


        fun newInstance(permission: String, titleText: String, bodyText: String, showIcon: Boolean, style: StyleObject): PermissionFragment {
            val fragment = PermissionFragment()
            val args = Bundle()
            args.putString(PERMISSION_EXTRA, permission)
            args.putString(TITLE_EXTRA, titleText)
            args.putString(BODY_EXTRA, bodyText)
            args.putBoolean(SHOW_ICON_EXTRA, showIcon)
            args.putSerializable(STYLE_EXTRA, style)
            fragment.arguments = args
            return fragment
        }
    }
}