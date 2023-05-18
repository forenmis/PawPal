package com.example.pawpal.screens.home.medicine.history_notification.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.screens.home.medicine.entity.Note
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NotificationDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val databaseManager = App.getInstance(application.applicationContext).databaseManager

    val notificationDetailsLD = MutableLiveData<PetNotification>()
    val notesLD = MutableLiveData<List<Note>>()


    fun loadNotificationById(id: Long) {
        val disposable = databaseManager.getNotificationById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({(notification, notes)->
                notificationDetailsLD.postValue(notification)
                notesLD.postValue(notes)
                       }, {})
        compositeDisposable.add(disposable)
    }

    fun saveNote(titleNote: String) {
        val notificationId = notificationDetailsLD.value?.id ?: error("notificationId not found")
        val note = Note(
            notificationId = notificationId,
            title = titleNote
        )
        val disposable = databaseManager.addNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadNotificationById(notificationId) }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}