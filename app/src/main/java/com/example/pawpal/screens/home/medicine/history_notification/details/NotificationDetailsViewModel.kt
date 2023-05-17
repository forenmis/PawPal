package com.example.pawpal.screens.home.medicine.history_notification.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NotificationDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val databaseManager = App.getInstance(application.applicationContext).databaseManager

    val notificationDetailsLD = MutableLiveData<PetNotification>()


    fun loadNotificationById(id: Long) {
        val disposable = databaseManager.getNotificationById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ notificationDetailsLD.postValue(it) }, {})
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}