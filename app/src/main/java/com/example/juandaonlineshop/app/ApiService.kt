package com.example.juandaonlineshop.app

import com.example.juandaonlineshop.model.ResponModel
import retrofit2.Call
import retrofit2.http.*
import okhttp3.ResponseBody


interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") nomortlp: String,
        @Field("password") password: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponModel>

    @GET ("produk")
    fun getProduk(): Call<ResponModel>
}