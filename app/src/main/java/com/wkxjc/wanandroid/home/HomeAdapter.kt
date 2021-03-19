package com.wkxjc.wanandroid.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.library.project.myStartActivity
import com.wkxjc.wanandroid.banner.ImageAdapter
import com.wkxjc.wanandroid.databinding.ItemArticleBinding
import com.wkxjc.wanandroid.databinding.ItemBannerBinding
import com.wkxjc.wanandroid.databinding.ItemLoadMoreBinding
import com.wkxjc.wanandroid.databinding.ItemShortcutBinding
import com.base.library.project.BaseViewHolder
import com.wkxjc.wanandroid.R
import com.wkxjc.wanandroid.common.artical.LINK
import com.wkxjc.wanandroid.common.artical.WebActivity
import com.wkxjc.wanandroid.common.banner.ImageAdapter
import com.wkxjc.wanandroid.home.common.bean.*
import com.wkxjc.wanandroid.home.commonWebSites.CommonWebsitesActivity
import com.wkxjc.wanandroid.home.knowledge.KnowledgeTreeActivity
import com.wkxjc.wanandroid.home.navigation.NavigationActivity
import com.wkxjc.wanandroid.home.publicAccounts.PublicAccountActivity
import com.youth.banner.listener.OnBannerListener


const val BANNER = 1
const val SHORTCUT = 2
const val ARTICLE = 0
const val LOAD_MORE = -1
const val HEADER_COUNT = 2
const val FOOTER_COUNT = 1
const val HEADER_FOOTER_COUNT = HEADER_COUNT + FOOTER_COUNT

class HomeAdapter(private val homeBean: HomeBean = HomeBean()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, bean: ArticleBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            BANNER -> BannerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(context), parent, false))
            SHORTCUT -> ShortcutViewHolder(ItemShortcutBinding.inflate(LayoutInflater.from(context), parent, false))
            LOAD_MORE -> LoadMoreViewHolder(ItemLoadMoreBinding.inflate(LayoutInflater.from(context), parent, false))
            else -> ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(context), parent, false))
        }
    }

    inner class BannerViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ShortcutViewHolder(val binding: ItemShortcutBinding) : RecyclerView.ViewHolder(binding.root)
    inner class LoadMoreViewHolder(val binding: ItemLoadMoreBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = homeBean.articles.datas.size + HEADER_FOOTER_COUNT

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> {
                holder.binding.banner.adapter = ImageAdapter(homeBean.banners.data)
                holder.binding.banner.setOnBannerListener(object : OnBannerListener<BannerBean> {
                    override fun OnBannerClick(data: BannerBean?, position: Int) {
                        context.myStartActivity<WebActivity>(LINK to data?.url)
                    }
                })
            }
            is ShortcutViewHolder -> {
                holder.binding.tvPublicAccounts.setOnClickListener {
                    context.myStartActivity<PublicAccountActivity>()
                }
                holder.binding.tvCommonWebsites.setOnClickListener {
                    context.myStartActivity<CommonWebsitesActivity>()
                }
                holder.binding.tvKnowledgeTree.setOnClickListener {
                    context.myStartActivity<KnowledgeTreeActivity>()
                }
                holder.binding.tvNavigation.setOnClickListener {
                    context.myStartActivity<NavigationActivity>()
                }
            }
            is ArticleViewHolder -> {
                val bean = homeBean.articles.datas[position - HEADER_COUNT]
                holder.binding.tvTitle.text = bean.title
                holder.binding.root.setOnClickListener {
                    onItemClickListener.onItemClick(it, bean)
                }
                holder.binding.tvCollect.text = context.getString(if (bean.collect) R.string.collected else R.string.collect)
                holder.binding.tvCollect.isEnabled = !bean.collect
                holder.binding.tvCollect.setOnClickListener {
                    if (!bean.collect) {
                        holder.itemView.tvCollect.isEnabled = false
                        holder.itemView.tvCollect.text = context.getString(R.string.collected)
                        onItemClickListener.onItemClick(it, bean)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getViewTypeByPosition(position)
    }

    private fun getViewTypeByPosition(position: Int): Int {
        return when (position) {
            0 -> BANNER
            1 -> SHORTCUT
            itemCount - 1 -> LOAD_MORE
            else -> ARTICLE
        }
    }

    fun refresh(banners: Banners, articles: Articles) {
        homeBean.refresh(banners, articles)
        notifyDataSetChanged()
    }

    fun addMore(articles: Articles) {
        homeBean.addMore(articles)
        notifyDataSetChanged()
    }

}