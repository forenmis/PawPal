package com.example.pawpal.screens.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.data.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {

    private val networkManager: NetworkManager
    private val compositeDisposable = CompositeDisposable()

    val isAddedLD = MutableLiveData(false)
    val processLD = MutableLiveData(false)
    val errorLD = MutableLiveData(false)
    val inputErrorsLD = MutableLiveData<List<FieldsError>>(emptyList())

    init {
        networkManager = App.getInstance(application.applicationContext).networkManager
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun addPet(name: String, age: Int) {
        inputErrorsLD.value = checkFields(name, age)
        if (inputErrorsLD.value?.isNotEmpty() == true) return
        val disposable = networkManager.addPet(name, age)
            .doOnSubscribe { processLD.postValue(true) }
            .doFinally { processLD.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { errorLD.postValue(true) })
        compositeDisposable.add(disposable)
    }

    private fun checkFields(name: String, age: Int): List<FieldsError> {
        val errors = mutableListOf<FieldsError>()
        if (name.length < 2) {
            errors.add(FieldsError.NameError)
        }
        if (age !in 1 until 100) {
            errors.add(FieldsError.AgeError)
        }
        return errors
    }
}

sealed class FieldsError {
    object AgeError : FieldsError()
    object NameError : FieldsError()
}//куда-то деть