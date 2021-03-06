package com.base.library.rxRetrofit.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Date:    2019-04-26
 */
interface TestApiService {
    @GET("v1/vertical/vertical")
    fun getRandomWallpaper(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0,
        @Query("adult") adult: Boolean = false,
        @Query("first") first: Int = 0,
        @Query("order") order: String = "hot"
    ): Observable<String>

    @GET("v1/vertical/category")
    fun getCategory(
        @Query("adult") adult: Boolean = false,
        @Query("first") first: Int = 1
    ): Observable<String>
}