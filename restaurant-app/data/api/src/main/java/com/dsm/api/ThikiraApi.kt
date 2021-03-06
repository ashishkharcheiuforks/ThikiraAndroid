package com.dsm.api

import com.dsm.api.response.*
import retrofit2.http.*
import java.util.*

interface ThikiraApi {

    @GET("/")
    suspend fun getRestaurantInfo(): RestaurantResponse

    @POST("create")
    suspend fun register(@Body body: Any)

    @DELETE("leave")
    suspend fun unregister()

    @GET("auth")
    suspend fun authToken()

    @FormUrlEncoded
    @PATCH("password")
    suspend fun changePassword(@Field("password") newPassword: String)

    /**
     * auth
     */
    @GET("auth/password")
    suspend fun confirmPassword(@Query("password") password: String)

    @GET("auth/email")
    suspend fun confirmEmailDuplication(@Query("email") email: String)

    @POST("auth/sign_in")
    suspend fun login(@Body body: Any): TokenResponse

    /**
     * menu
     */
    @GET("menu")
    suspend fun getMenus(@Query("mc_id") menuCategoryId: Int): List<MenuResponse>

    @GET("menu/category")
    suspend fun getMenuCategories(): List<MenuCategoryResponse>

    @POST("menu")
    suspend fun uploadMenu(@Body body: Any)

    /**
     * menu category
     */
    @DELETE("menu/category/{mc_ids}")
    suspend fun deleteMenuCategories(@Path("mc_ids") menuCategoryIds: List<Int>)

    @FormUrlEncoded
    @PATCH("menu/category")
    suspend fun updateMenuCategoryName(
        @Field("mc_id") menuCategoryId: Int,
        @Field("name") name: String
    )

    /**
     * coupon
     */
    @FormUrlEncoded
    @POST("coupon")
    suspend fun issueCoupon(
        @Field("expired_day") expireDate: Date,
        @Field("discount_amount") price: Int
    )

    @GET("coupon")
    suspend fun getCoupons(): List<CouponResponse>
}
