package com.wkxjc.wanandroid

import android.app.Application
import com.base.library.BaseApp
import com.base.library.rxRetrofit.RxRetrofitApp
import com.wkxjc.wanandroid.common.httpManager.MyApiConfig

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BaseApp.init(this)
        RxRetrofitApp.apiConfig = MyApiConfig()
    }
}

