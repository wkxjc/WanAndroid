package com.wkxjc.wanandroid.common.bean

data class HomeBean(
    val banners: Banners = Banners(),
    val articles: Articles = Articles()
) {
    fun refresh(banners: Banners, articles: Articles) {
        this.banners.refresh(banners)
        this.articles.refresh(articles)
    }

    fun loadMore(articles: Articles) {
        this.articles.loadMore(articles)
    }
}