package com.base.library.banner

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.base.library.banner.ImageAdapter.BannerViewHolder
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(data: List<String>) : BannerAdapter<String, BannerViewHolder>(data) {
    private lateinit var context: Context
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        context = parent.context
        val imageView = ImageView(parent.context)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: String, position: Int, size: Int) {
        Glide.with(context).load(data).into(holder.imageView)
        holder.itemView.setOnClickListener {
        }
    }

    class BannerViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}