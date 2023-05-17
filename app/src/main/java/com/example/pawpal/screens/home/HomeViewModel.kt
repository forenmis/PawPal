package com.example.pawpal.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val processExitLD = MutableLiveData(false)
    val navigationLD = MutableLiveData(false)
    val idNotificationLD = MutableLiveData<Long>()

    private val appPreference = App.getInstance(application).appPreference
    private val compositeDisposable = CompositeDisposable()

    fun exit() {
        val disposable = Completable.create { emitter ->
            appPreference.clear()
            if (!emitter.isDisposed) emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { processExitLD.postValue(true) }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun navigateToCreateNotification() {
        navigationLD.value = true
    }


}