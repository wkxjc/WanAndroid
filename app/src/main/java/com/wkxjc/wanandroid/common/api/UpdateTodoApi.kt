package com.wkxjc.wanandroid.common.api

import com.base.library.rxRetrofit.common.utils.SPUtils
import com.base.library.rxRetrofit.http.api.BaseApi
import com.wkxjc.wanandroid.common.user.LOGIN_INFO
import com.wkxjc.wanandroid.common.bean.TodoBean
import io.reactivex.Observable
import okhttp3.Headers

class UpdateTodoApi(val bean: TodoBean) : BaseApi() {
    override fun getObservable(): Observable<String> {
        apiConfig.headers = Headers.headersOf(COOKIE_HEADER_KEY, SPUtils.getInstance(LOGIN_INFO).getString(COOKIE))
        val apiService = retrofit.create(ApiService::class.java)
        return apiService.updateTodo(id = bean.id!!, title = bean.title, date = bean.dateStr!!)
    }

}