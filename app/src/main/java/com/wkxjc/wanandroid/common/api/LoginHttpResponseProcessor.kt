package com.wkxjc.wanandroid.common.api

import com.base.library.rxRetrofit.RxRetrofitApp
import com.base.library.rxRetrofit.common.header.IHttpResponseProcessor
import com.base.library.rxRetrofit.common.utils.SPUtils
import com.wkxjc.wanandroid.common.user.LOGIN_INFO
import okhttp3.Cookie
import okhttp3.Request
import okhttp3.Response

const val COOKIE_HEADER_KEY = "Cookie"
const val COOKIE = "Cookie"

class LoginHttpResponseProcessor : IHttpResponseProcessor {
    override fun handleResponse(request: Request, response: Response): Response {
        // 获取头部的 Cookie
        val cookies: List<Cookie> = Cookie.parseAll(request.url, response.headers)
        if (cookies.isNotEmpty()) {
            // 去掉 List 转 String 的中括号
            SPUtils.getInstance(LOGIN_INFO).put(COOKIE, cookies.toString().substring(1, cookies.toString().length - 2))
        }
        return RxRetrofitApp.apiConfig.httpResponseProcessor.handleResponse(request, response)
    }
}