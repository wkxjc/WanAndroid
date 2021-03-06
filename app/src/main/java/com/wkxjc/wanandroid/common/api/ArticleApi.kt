package com.wkxjc.wanandroid.common.api

import com.alibaba.fastjson.JSON
import com.base.library.rxRetrofit.common.utils.SPUtils
import com.base.library.rxRetrofit.http.api.BaseApi
import com.wkxjc.wanandroid.common.bean.Articles
import com.wkxjc.wanandroid.common.user.LOGIN_INFO
import io.reactivex.Observable
import okhttp3.Headers
import java.util.*

class ArticleApi : BaseApi() {

    init {
        // close cache so that article collection state can be updated
        apiConfig.cacheConfig.cache = false
    }

    private var page = 0
    override fun getObservable(): Observable<String> {
        apiConfig.headers = Headers.headersOf(COOKIE_HEADER_KEY, SPUtils.getInstance(LOGIN_INFO).getString(COOKIE))
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.getHomeArticles(page)
    }

    fun resetPage() {
        page = 0
    }

    fun nextPage() {
        page++
    }

    fun convert(resultMap: HashMap<BaseApi, Any>): Articles {
        return JSON.parseObject(resultMap[this] as String, Articles::class.javaObjectType)
    }

    fun convert(result: String): Articles {
        return JSON.parseObject(result, Articles::class.javaObjectType)
    }

}