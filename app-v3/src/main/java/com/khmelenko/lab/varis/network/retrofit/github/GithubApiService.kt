package com.khmelenko.lab.varis.network.retrofit.github

import com.khmelenko.lab.varis.network.request.AuthorizationRequest
import com.khmelenko.lab.varis.network.response.Authorization
import com.khmelenko.lab.varis.network.response.OAuthUser

import io.reactivex.Single
import retrofit2.http.*

const val TWO_FACTOR_HEADER = "X-GitHub-OTP"

/**
 * Defines API for working with Github
 *
 * @author Dmytro Khmelenko
 */
interface GithubApiService {

    @POST("/authorizations")
    fun createNewAuthorization(@Header("Authorization") basicAuth: String,
                               @Body authorizationRequest: AuthorizationRequest): Single<Authorization>

    @POST("/authorizations")
    fun createNewAuthorization(@Header("Authorization") basicAuth: String,
                               @Header(TWO_FACTOR_HEADER) twoFactorCode: String,
                               @Body authorizationRequest: AuthorizationRequest): Single<Authorization>

    @DELETE("/authorizations/{authorizationId}")
    fun deleteAuthorization(@Header("Authorization") basicAuth: String,
                            @Path("authorizationId") authorizationId: String): Single<Any>

    @DELETE("/authorizations/{authorizationId}")
    fun deleteAuthorization(@Header("Authorization") basicAuth: String,
                            @Header(TWO_FACTOR_HEADER) twoFactorCode: String,
                            @Path("authorizationId") authorizationId: String): Single<Any>

    @GET("/user")
    fun createOAuthAuthentication(@Query("access_token") token: String): Single<OAuthUser>
}
