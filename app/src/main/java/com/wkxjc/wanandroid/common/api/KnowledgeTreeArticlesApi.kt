package com.wkxjc.wanandroid.common.api

import com.alibaba.fastjson.JSON
import com.base.library.rxRetrofit.http.api.BaseApi
import com.wkxjc.wanandroid.common.bean.Articles
import io.reactivex.Observable

class KnowledgeTreeArticlesApi(private val categoryId: Int, var page: Int = 0) : BaseApi() {

    override fun getObservable(): Observable<String> {
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.getArticlesByCategoryId(page, categoryId)
    }

    fun convert(result: String): Articles {
        return JSON.parseObject(result, Articles::class.javaObjectType)
    }
}