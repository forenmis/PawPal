package com.example.pawpal.data.network

import com.example.pawpal.data.network.entity.NetworkUser
import com.example.pawpal.data.network.entity.request.AddPetRequest
import com.example.pawpal.data.network.entity.request.RegistrationRequest
import com.example.pawpal.data.network.entity.response.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {

    @GET("login")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1"
    )
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Single<LoginResponse>

    @POST("users")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1",
        "Content-Type: application/json"
    )
    fun registration(@Body request: RegistrationRequest): Single<RegistrationResponse>

    @POST("classes/Paw")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1",
        "Content-Type: application/json"
    )
    fun addPet(
        @Body request: AddPetRequest,
        @Header("X-Parse-Session-Token") token: String
    ): Completable

    @GET("classes/Paw")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1"
    )
    fun getPets(
        @Query("where") whereJson: String,
        @Header("X-Parse-Session-Token") token: String
    ): Single<GetPetResponse>


    @GET("classes/Toys")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
    )
    fun getToys(
        @Header("X-Parse-Session-Token") token: String
    ): Single<GetToyResponse>

    @GET("classes/Food")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
    )
    fun getFood(
        @Header("X-Parse-Session-Token") token: String
    ): Single<GetFoodResponse>

    @GET("classes/Banner")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
    )
    fun getBanner(
        @Header("X-Parse-Session-Token") token: String
    ): Single<GetBannerResponse>


    @GET("users/me")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "X-Parse-Revocable-Session: 1"
    )
    fun getCurrentUser(@Header("X-Parse-Session-Token") token: String): Single<NetworkUser>


    @Multipart
    @POST
    fun uploadAvatar(
        @Url url: String = "https://api.imgbb.com/1/upload",
        @Query("key") key: String = "451e5f75182faf09b2fe6e08d52b4ac6",
        @Part multipart: MultipartBody.Part
    ): Single<PostUploadAvatarResponse>


    @PUT("users/{userId}")
    @Headers(
        "X-Parse-Application-Id: pgVc6wVrzVehS6Ji7cggcDDDNqSc89wUwtXIRyTl",
        "X-Parse-REST-API-Key: pX6ubHbyVkwtdyBs2spm0F6UqEgV8NwKybApCw0A",
        "Content-Type: application/json"
    )
    fun updateAvatar(
        @Header("X-Parse-Session-Token") token: String,
        @Path("userId") userId: String,
        @Body body: UpdateAvatarRequest
    ): Completable
}




