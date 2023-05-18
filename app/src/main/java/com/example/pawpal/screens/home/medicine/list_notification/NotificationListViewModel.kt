package com.example.pawpal.screens.home.medicine.list_notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.R
import com.example.pawpal.app.App
import com.example.pawpal.data.database.DatabaseManager
import com.example.pawpal.screens.home.medicine.list_notification.entity.ItemNotifications
import com.example.pawpal.utils.getDateInStr
import com.example.pawpal.utils.isTheSameDay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date

class NotificationListViewModel(private val application: Application) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val databaseManager: DatabaseManager =
        App.getInstance(application.applicationContext).databaseManager

    val listItemsLD = MutableLiveData<List<ItemNotifications>>()

    init {
        loadList()
    }

    fun loadList() {
        val disposable = databaseManager.getAllNotifications()
            .map { petNotifications ->
                val items = mutableListOf<ItemNotifications>()
                val periodicList = petNotifications.filter { it.isPeriod }
                val now = Date()
                val unPeriodicList = petNotifications.filter { !it.isPeriod && it.date.after(now) }
                    .sortedBy { it.date }
                if (periodicList.isNotEmpty()) {
                    items.add(ItemNotifications.GroupItem(application.getString(R.string.periodic_notifications)))
                    periodicList.forEach { items.add(ItemNotifications.NotificationItem(it)) }
                }
                var lastDate: Date? = null
                unPeriodicList.forEach { petNotification ->
                    if (!petNotification.date.isTheSameDay(lastDate)) {
                        items.add(ItemNotifications.GroupItem(petNotification.date.getDateInStr()))
                        lastDate = petNotification.date
                    }
                    items.add(ItemNotifications.NotificationItem(petNotification))
                }
                return@map items
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listItemsLD.postValue(it) }, {})
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}