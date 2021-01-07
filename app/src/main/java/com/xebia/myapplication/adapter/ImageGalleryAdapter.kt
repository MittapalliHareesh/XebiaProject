package com.xebia.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xebia.myapplication.R
import com.xebia.myapplication.model.Entry
import kotlinx.android.synthetic.main.item_layout.view.*
import java.lang.ref.WeakReference

class ImageGalleryAdapter(
    var entryList: ArrayList<Entry>,
    private val listener: OnEntryClickListener
) : RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageGalleryAdapter.ImageGalleryHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_layout, parent,
            false
        )
        return ImageGalleryHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    override fun onBindViewHolder(holder: ImageGalleryHolder, position: Int) {
        holder.bind()
    }

    inner class ImageGalleryHolder(itemView: View, listener: OnEntryClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val listenerRef : WeakReference<OnEntryClickListener> = WeakReference(listener)

        fun bind() {
            val requestOptions =
                RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)

            Glide.with(context!!).applyDefaultRequestOptions(requestOptions)
                .load(entryList[adapterPosition].link.imageLink)
                .into(itemView.image_view)
            if (entryList[adapterPosition].title!!.isEmpty())
                itemView.tittle.text =
                    entryList[adapterPosition].authorDetails.authorName
            else
                itemView.tittle.text = entryList[adapterPosition].title

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listenerRef.get()?.onClickEntry(entryList[adapterPosition])
        }
    }

    //interface for getting on click listener
    interface OnEntryClickListener {
        fun onClickEntry(entry: Entry)
    }
}