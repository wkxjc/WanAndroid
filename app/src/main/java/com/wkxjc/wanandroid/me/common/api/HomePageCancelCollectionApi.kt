package com.wkxjc.wanandroid.me.common.api

import com.base.library.rxRetrofit.common.utils.SPUtils
import com.base.library.rxRetrofit.http.api.BaseApi
import com.wkxjc.wanandroid.home.common.api.ApiService
import io.reactivex.Observable
import okhttp3.Headers

class HomePageCancelCollectionApi : BaseApi() {
    var articleId = 0

    override fun getObservable(): Observable<String> {
        apiConfig.headers = Headers.headersOf(COOKIE_HEADER_KEY, SPUtils.getInstance(LOGIN_INFO).getString(COOKIE))
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.unCollectArticle(articleId)
    }
}