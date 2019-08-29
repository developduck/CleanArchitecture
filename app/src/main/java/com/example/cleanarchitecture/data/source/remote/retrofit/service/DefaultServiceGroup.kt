package com.example.cleanarchitecture.data.source.remote.retrofit.service

import com.example.cleanarchitecture.data.model.Model.Root
import com.example.cleanarchitecture.data.model.Model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Hwang on 2018-03-19.
 *
 * Description : Retrofit 라이브러리를 위한 기본 서비스 그룹
 */
interface DefaultServiceGroup {
    interface UserService {
        @GET("/test/get_users.php")
        fun getUsers(@QueryMap params: Map<String, String> ): Observable<Response<Root<List<User>>>>
        @GET("/test/get_user.php")
        fun getUser(@Query("id") userId: Int): Observable<Response<Root<User>>>
    }
}