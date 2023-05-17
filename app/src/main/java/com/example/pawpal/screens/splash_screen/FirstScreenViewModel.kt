package com.example.pawpal.screens.splash_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class FirstScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val appPreference = App.getInstance(application).appPreference

    val actionLD = MutableLiveData<Action>()

    fun checkScreen() {
        Completable.complete()
            .doOnSubscribe {
                if (appPreference.getToken() != null) {
                    actionLD.postValue(Action.ToHome)
                } else {
                    actionLD.postValue(Action.ToLogin)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}

