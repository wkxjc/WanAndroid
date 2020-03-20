package com.wkxjc.wanandroid.home.common.bean

data class Articles(
    val curPage: Int = 0,
    val data: MutableList<ArticleBean>,
    val offset: Int = 0,
    // TODO:what is this?
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
)