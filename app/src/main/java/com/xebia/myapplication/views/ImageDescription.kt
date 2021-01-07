package com.xebia.myapplication.views

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xebia.myapplication.R
import com.xebia.myapplication.model.AppConstants
import com.xebia.myapplication.model.Entry
import kotlinx.android.synthetic.main.activity_image_description.*
import kotlinx.android.synthetic.main.item_layout.view.*

class ImageDescription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_description)
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { this.onBackPressed() }

        val requestOptions =
            RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)

        val entry = intent.getParcelableExtra<Entry>(AppConstants.key)

        Glide.with(this).applyDefaultRequestOptions(requestOptions).load(entry?.link?.imageLink)
            .into(imageDesc)

        descTxt.text = String.format(
            resources.getString(R.string.metaDataInfo),
            entry?.title,
            entry?.authorDetails?.authorName,
            entry?.id,
            entry?.publishedDt,
            entry?.updatedDt
        )
    }
}