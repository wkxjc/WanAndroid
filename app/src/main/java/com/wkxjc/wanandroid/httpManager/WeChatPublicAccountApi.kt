package com.wkxjc.wanandroid.httpManager

import com.base.library.rxRetrofit.http.api.BaseApi
import com.wkxjc.wanandroid.home.common.api.ApiService
import io.reactivex.Observable

class WeChatPublicAccountApi : BaseApi() {
    override fun getObservable(): Observable<String> {
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.getPublicAccount()
    }
}