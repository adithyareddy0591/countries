package com.android.adithya.countries.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadCountryImage(path: String?) {
    Picasso.get()
        .load(path)
        .into(this)

}