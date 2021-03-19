package com.wkxjc.wanandroid.home.knowledge.knowledgeTreeArticles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.library.project.myStartActivity
import com.wkxjc.wanandroid.artical.LINK
import com.wkxjc.wanandroid.artical.WebActivity
import com.wkxjc.wanandroid.databinding.ItemKnowledgeTreeArticleBinding
import com.wkxjc.wanandroid.home.common.bean.Articles


class KnowledgeTreeArticlesAdapter(private val articles: Articles = Articles()) : RecyclerView.Adapter<KnowledgeTreeArticlesAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(val binding: ItemKnowledgeTreeArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemKnowledgeTreeArticleBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = articles.datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = articles.datas[position]
        holder.binding.tvKnowledgeTreeArticleTitle.text = bean.title
        holder.binding.root.setOnClickListener {
            context.myStartActivity<WebActivity>(LINK to bean.link)
        }
    }

    fun refresh(articles: Articles) {
        this.articles.refresh(articles)
        notifyDataSetChanged()
    }
}