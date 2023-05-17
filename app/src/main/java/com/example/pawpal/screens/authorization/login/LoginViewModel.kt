package com.example.pawpal.screens.authorization.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.data.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val networkManager: NetworkManager
    private val compositeDisposable = CompositeDisposable()

    val processLoginLD = MutableLiveData(false)
    val errorLD = MutableLiveData(false)
    val screenLD = MutableLiveData<VariantScreen?>()

    init {
        networkManager = App.getInstance(application.applicationContext).networkManager
    }

    fun login(username: String, password: String) {
        val disposable = networkManager.login(username = username, password = password)
            .concatMap { networkManager.hasUserPets() }
            .doOnSubscribe { processLoginLD.postValue(true) }
            .doFinally { processLoginLD.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { screenLD.value = if (it) VariantScreen.Home else VariantScreen.Onboarding },
                { errorLD.postValue(true) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}

sealed class VariantScreen {
    object Home : VariantScreen()
    object Onboarding : VariantScreen()
}