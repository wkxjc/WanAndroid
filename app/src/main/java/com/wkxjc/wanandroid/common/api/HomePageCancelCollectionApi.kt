package com.wkxjc.wanandroid.common.api

import com.base.library.rxRetrofit.common.utils.SPUtils
import com.base.library.rxRetrofit.http.api.BaseApi
import io.reactivex.Observable
import okhttp3.Headers
import com.wkxjc.wanandroid.common.user.LOGIN_INFO

class HomePageCancelCollectionApi(private var articleId: Int) : BaseApi() {

    override fun getObservable(): Observable<String> {
        apiConfig.headers = Headers.headersOf(COOKIE_HEADER_KEY, SPUtils.getInstance(LOGIN_INFO).getString(COOKIE))
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.unCollectArticle(articleId)
    }
}