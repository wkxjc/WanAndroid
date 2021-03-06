package com.base.library.rxRetrofit

import android.app.Application
import com.base.library.rxRetrofit.download.config.DefaultDownConfig
import com.base.library.rxRetrofit.http.api.ApiConfig
import com.base.library.rxRetrofit.http.httpList.DefaultHttpListConfig

/**
 * Description:
 * 全局application
 *
 * @author  WZG
 * Date:    2019-04-25
 */
object RxRetrofitApp {
    /**全局Application*/
    @JvmStatic
    lateinit var application: Application
    /**全局BaseApi配置*/
    @JvmStatic
    var apiConfig: ApiConfig = ApiConfig()
    /**全局HttpList配置*/
    @JvmStatic
    var httpListConfig: DefaultHttpListConfig = DefaultHttpListConfig()
    /**全局DownConfig配置*/
    @JvmStatic
    var downConfig: DefaultDownConfig = DefaultDownConfig()
}
