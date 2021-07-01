package dev.yunzai.milibrary.base.appbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import dev.yunzai.milibrary.R

class BaseAppBar(
    private val context: Context,
    private val actionBar: ActionBar?
) {
    private var isActionBarSet = false
    private val customAppBarView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.app_bar_base, null)
    }
    var leftButtonClickListener: ((View) -> Unit)? = null
    var rightButtonClickListener: ((View) -> Unit)? = null

    fun setUpActionBar() {
        if (actionBar == null) return
        val params = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )

        with(actionBar) {
            setCustomView(customAppBarView, params)
            setDisplayShowCustomEnabled(true)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(false)
            val parent = customAppBarView.parent
            if (parent is Toolbar) {
                parent.setContentInsetsAbsolute(0, 0)
            }
        }
        isActionBarSet = true
    }

    fun setTitle(title: String) {
        checkCustomActionbarSet()
        val titleTextView = customAppBarView.findViewById<TextView>(R.id.title_text_view)
        titleTextView.text = title
    }

    fun setLeftButtonDrawable(@DrawableRes resId: Int) {
        checkCustomActionbarSet()
        val leftButton = customAppBarView.findViewById<ImageView>(R.id.left_button)
        leftButton.visibility = View.VISIBLE
        leftButton.setImageResource(resId)
        leftButton.setColorFilter(ResourcesCompat.getColor(context.resources, R.color.primaryTextColor, null))
        leftButton.setOnClickListener {
            leftButtonClickListener?.invoke(leftButton)
        }
    }

    fun setRightButtonDrawable(@DrawableRes resId: Int) {
        checkCustomActionbarSet()
        val rightButton = customAppBarView.findViewById<ImageView>(R.id.right_button)
        rightButton.visibility = View.VISIBLE
        rightButton.setImageResource(resId)
        rightButton.setColorFilter(ResourcesCompat.getColor(context.resources, R.color.primaryTextColor, null))
        rightButton.setOnClickListener {
            rightButtonClickListener?.invoke(rightButton)
        }
    }

    private fun checkCustomActionbarSet() {
        if (!isActionBarSet)
            throw IllegalStateException("Need to set action bar first")
    }
}
