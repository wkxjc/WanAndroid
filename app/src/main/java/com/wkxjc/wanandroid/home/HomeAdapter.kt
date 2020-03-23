package com.wkxjc.wanandroid.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.library.banner.ImageAdapter
import com.base.library.project.BaseViewHolder
import com.wkxjc.wanandroid.R
import com.wkxjc.wanandroid.artical.LINK
import com.wkxjc.wanandroid.artical.WebActivity
import com.wkxjc.wanandroid.home.common.bean.Articles
import com.wkxjc.wanandroid.home.common.bean.Banners
import com.wkxjc.wanandroid.home.common.bean.HomeBean
import com.wkxjc.wanandroid.home.commonWebSites.CommonWebsitesActivity
import com.wkxjc.wanandroid.home.knowledge.KnowledgeTreeActivity
import com.wkxjc.wanandroid.home.navigation.NavigationActivity
import com.wkxjc.wanandroid.home.publicAccounts.PublicAccountActivity
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.item_article.view.*
import kotlinx.android.synthetic.main.item_banner.view.*
import kotlinx.android.synthetic.main.item_shortcut.view.*
import org.jetbrains.anko.startActivity

const val BANNER = 1
const val SHORTCUT = 2
const val ARTICLE = 0
const val LOAD_MORE = -1
const val HEADER_COUNT = 2
const val FOOTER_COUNT = 1
const val HEADER_FOOTER_COUNT = HEADER_COUNT + FOOTER_COUNT

class HomeAdapter(private val homeBean: HomeBean = HomeBean()) : RecyclerView.Adapter<BaseViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        return BaseViewHolder(LayoutInflater.from(context).inflate(getLayoutIdByViewType(viewType), parent, false))
    }

    override fun getItemCount() = homeBean.articles.datas.size + HEADER_FOOTER_COUNT

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getViewTypeByPosition(position)) {
            BANNER -> {
                val banner: Banner<String, ImageAdapter> = holder.itemView.banner as Banner<String, ImageAdapter>
                banner.adapter = ImageAdapter(homeBean.banners.data.map { it.imagePath })
                banner.setOnBannerListener(object : OnBannerListener<String> {
                    override fun onBannerChanged(position: Int) {
                    }

                    override fun OnBannerClick(data: String?, position: Int) {
                        Log.d("~~~", data)
                        context.startActivity<WebActivity>(LINK to homeBean.banners.data[position])
                    }
                })
            }
            SHORTCUT -> {
                holder.itemView.tvPublicAccounts.setOnClickListener {
                    context.startActivity<PublicAccountActivity>()
                }
                holder.itemView.tvCommonWebsites.setOnClickListener {
                    context.startActivity<CommonWebsitesActivity>()
                }
                holder.itemView.tvKnowledgeTree.setOnClickListener {
                    context.startActivity<KnowledgeTreeActivity>()
                }
                holder.itemView.tvNavigation.setOnClickListener {
                    context.startActivity<NavigationActivity>()
                }
            }
            ARTICLE -> {
                val bean = homeBean.articles.datas[position - HEADER_COUNT]
                holder.itemView.tvTitle.text = bean.title
                holder.itemView.setOnClickListener {
                    context.startActivity<WebActivity>(LINK to bean.link)
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

    private fun getLayoutIdByViewType(viewType: Int): Int {
        return when (viewType) {
            BANNER -> R.layout.item_banner
            SHORTCUT -> R.layout.item_shortcut
            LOAD_MORE -> R.layout.item_load_more
            else -> R.layout.item_article
        }
    }

    fun refresh(banners: Banners, articles: Articles) {
        homeBean.refresh(banners, articles)
        notifyDataSetChanged()
    }

}