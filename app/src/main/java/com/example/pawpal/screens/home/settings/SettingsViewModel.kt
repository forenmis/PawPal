package com.example.pawpal.screens.home.settings

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.data.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val networkManager: NetworkManager
    private val compositeDisposable = CompositeDisposable()

    val profileDataLD = MutableLiveData<ProfileData>()
    val processLD = MutableLiveData(false)


    init {
        networkManager = App.getInstance(application.applicationContext).networkManager
        loadProfile()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun uploadAvatar(uri: Uri) {
        val disposable = networkManager.uploadAvatar(uri)
            .doOnSubscribe { processLD.postValue(true) }
            .delay(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { processLD.postValue(false) }
            .concatMapCompletable { avatar ->
                networkManager.updateAvatar(avatar)
            }
            .andThen(networkManager.getCurrentUser())
            .subscribe({ user ->
                profileDataLD.postValue(profileDataLD.value!!.copy(user = user))
            }, {})
        compositeDisposable.add(disposable)
    }

    private fun loadProfile() {
        val disposable = Single.zip(
            networkManager.getCurrentUser(),
            networkManager.getPets()
        ) { user, pet -> ProfileData(user, pet) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { profileData -> profileDataLD.postValue(profileData) }
        compositeDisposable.add(disposable)
    }

    fun deleteUserAvatar() {
        val disposable = networkManager.updateAvatar(null)
            .andThen(networkManager.getCurrentUser())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                profileDataLD.postValue(profileDataLD.value!!.copy(user = user))
            }, {})
        compositeDisposable.add(disposable)
    }
}

