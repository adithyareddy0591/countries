package com.android.adithya.countries.utils

import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation

object AnimUtils {
    fun createBgImageInAnimation(fromX: Int, toX: Int, transitionDuration: Int): Animation {
        val translate = TranslateAnimation(fromX.toFloat(), toX.toFloat(), 0F, 0F)
        translate.duration = transitionDuration.toLong()
        val set = AnimationSet(true)
        set.interpolator = DecelerateInterpolator()
        set.addAnimation(translate)
        return set
    }

    fun createBgImageOutAnimation(fromX: Int, toX: Int, transitionDuration: Int): Animation {
        val ta = TranslateAnimation(fromX.toFloat(), toX.toFloat(), 0F, 0F)
        ta.duration = transitionDuration.toLong()
        ta.interpolator = DecelerateInterpolator()
        return ta
    }
}