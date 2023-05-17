package com.example.pawpal.data.network

import android.content.Context
import android.net.Uri
import com.example.pawpal.app.App
import com.example.pawpal.app.app_preference.AppPreference
import com.example.pawpal.data.network.entity.request.AddPetRequest
import com.example.pawpal.data.network.entity.request.GetPetRequest
import com.example.pawpal.data.network.entity.request.RegistrationRequest
import com.example.pawpal.data.network.entity.response.UpdateAvatarRequest
import com.example.pawpal.data.network.entity.toBanner
import com.example.pawpal.data.network.entity.toFood
import com.example.pawpal.data.network.entity.toPet
import com.example.pawpal.data.network.entity.toToy
import com.example.pawpal.data.network.entity.toUser
import com.example.pawpal.entity.Banner
import com.example.pawpal.entity.Food
import com.example.pawpal.entity.Pet
import com.example.pawpal.entity.Toy
import com.example.pawpal.entity.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager(private val context: Context) {

    private val api: Api
    private val appPreference: AppPreference
    private val gson: Gson

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder().baseUrl("https://parseapi.back4app.com/").client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()

        api = retrofit.create(Api::class.java)
        appPreference = App.getInstance(context).appPreference
    }

    fun registration(password: String, username: String, email: String): Single<String> {
        val request = RegistrationRequest(password, username, email)
        return api.registration(request)
            .doOnSuccess { response ->
                appPreference.saveToken(response.sessionToken)
                appPreference.saveUserId(response.userId)
            }
            .map { it.userId }
    }

    fun login(username: String, password: String): Single<String> {
        return api.login(username, password)
            .doOnSuccess { response ->
                appPreference.saveToken(response.sessionToken)
                appPreference.saveUserId(response.userId)
            }
            .map { it.userId }
    }

    fun hasUserPets(): Single<Boolean> {
        val request = GetPetRequest(requireNotNull(appPreference.getUserId()))
        val json = gson.toJson(request)
        return api.getPets(json, requireNotNull(appPreference.getToken()))
            .map { it.results.isNotEmpty() }
    }

    fun getPets(): Single<Pet> {
        val request = GetPetRequest(requireNotNull(appPreference.getUserId()))
        val json = gson.toJson(request)
        return api.getPets(json, requireNotNull(appPreference.getToken())).map { responseList ->
            responseList.results[0].toPet()
        }
    }

    fun addPet(name: String, age: Int): Completable {
        val request = AddPetRequest(
            name = name,
            age = age,
            userId = requireNotNull(appPreference.getUserId())
        )
        return api.addPet(request, requireNotNull(appPreference.getToken()))
    }

    fun getToys(): Single<List<Toy>> {
        return api.getToys(requireNotNull(appPreference.getToken())).map { response ->
            response.results.map { it.toToy() }
        }
    }

    fun getFood(): Single<List<Food>> {
        return api.getFood(requireNotNull(appPreference.getToken())).map { foodResponse ->
            foodResponse.results.map { it.toFood() }
        }
    }

    fun getBanner(): Single<Banner> {
        return api.getBanner(requireNotNull(appPreference.getToken())).map { listBanner ->
            listBanner.results[0].toBanner()
        }
    }

    fun getCurrentUser(): Single<User> {
        return api.getCurrentUser(requireNotNull(appPreference.getToken()))
            .map { it.toUser() }
    }

    fun uploadAvatar(uri: Uri): Single<String> {
        val filePart = MultipartBody.Part.createFormData(
            "image",
            "user_avatar.jpeg",
            ContentUriRequestBody(context.contentResolver, uri)
        )
        return api.uploadAvatar(multipart = filePart).map { response ->
            response.data.url
        }
    }

    fun updateAvatar(avatar: String?): Completable {
        return api.updateAvatar(
            requireNotNull(appPreference.getToken()),
            requireNotNull(appPreference.getUserId()),
            UpdateAvatarRequest(avatar)
        )
    }
}