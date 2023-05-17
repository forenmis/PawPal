package com.example.pawpal.screens.home.medicine.create_notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.pawpal.app.App
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import com.example.pawpal.utils.AlarmHelper
import com.example.pawpal.utils.getDateInStr
import com.example.pawpal.utils.getFullDate
import com.example.pawpal.utils.getTimeInStr
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class CreateNotificationViewModel(private val application: Application) :
    AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    private val databaseManager = App.getInstance(application.applicationContext).databaseManager

    private val notificationTitleLD = MutableLiveData<String>()
    private val notificationIsPeriodLD = MutableLiveData(false)
    private val notificationPeriodLD = MutableLiveData<Int>()
    private val notificationRemindLD = MutableLiveData<Int>()

    val processLD = MutableLiveData<Boolean>()
    val timeLD = MutableLiveData<String>()
    val dateLD = MutableLiveData<String>()

    private val notificationDateLD = MediatorLiveData<Date?>().apply {
        val listener: Observer<Any> = Observer<Any> {
            value = if (timeLD.value != null && dateLD.value != null) {
                getFullDate(dateLD.value!!, timeLD.value!!)
            } else null
        }
        addSource(timeLD, listener)
        addSource(dateLD, listener)
    }

    val completeFillLD = MediatorLiveData<Boolean>().apply {
        val listener: Observer<Any?> = Observer<Any?> {
            value = !notificationTitleLD.value.isNullOrEmpty() &&
                    notificationDateLD.value != null &&
                    notificationRemindLD.value != null &&
                    if (notificationIsPeriodLD.value == true) {
                        notificationPeriodLD.value != null
                    } else true
        }
        addSource(notificationTitleLD, listener)
        addSource(notificationDateLD, listener)
        addSource(notificationIsPeriodLD, listener)
        addSource(notificationPeriodLD, listener)
        addSource(notificationRemindLD, listener)
    }

    private fun createNotification(): PetNotification {
        return PetNotification(
            title = notificationTitleLD.value ?: error("title is missing"),
            date = notificationDateLD.value ?: error("date is missing"),
            isPeriod = notificationIsPeriodLD.value ?: error("isPeriod is missing"),
            periodDays = notificationPeriodLD.value,
            remindIn = notificationRemindLD.value ?: error("remind is missing")
        )
    }

    fun saveNotification() {
        val petNotification = createNotification()
        val disposable = databaseManager.addPetNotification(petNotification)
            .doOnSubscribe { processLD.postValue(true) }
            .delay(3, TimeUnit.SECONDS)
            .doFinally { processLD.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ AlarmHelper.createTask(application, petNotification) }, {})
        compositeDisposable.add(disposable)
    }

    fun changeDate(newDate: Long) {
        val date = Date(newDate)
        if (dateLD.value != date.getDateInStr()) {
            dateLD.postValue(date.getDateInStr())
        }
    }

    fun changeTime(hours: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        if (timeLD.value != calendar.getTimeInStr()) {
            timeLD.postValue(calendar.getTimeInStr())
        }
    }

    fun changeTitle(newTitle: String) {
        if (notificationTitleLD.value != newTitle) {
            notificationTitleLD.postValue(newTitle)
        }
    }

    fun changeIsPeriod(boolean: Boolean) {
        notificationIsPeriodLD.postValue(boolean)
    }

    fun changePeriod(newPeriod: Int) {
        if (notificationPeriodLD.value != newPeriod) {
            notificationPeriodLD.postValue(newPeriod)
        }
    }

    fun changeRemind(newRemind: Int) {
        if (notificationRemindLD.value != newRemind) {
            notificationRemindLD.postValue(newRemind)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}