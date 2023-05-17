package com.example.pawpal.screens.authorization.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.data.network.NetworkManager
import com.example.pawpal.screens.authorization.InputError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    val processRegistrationLD = MutableLiveData(false)
    val errorLD = MutableLiveData(false)
    val inputErrorsLD = MutableLiveData<List<InputError>>(emptyList())
    val registeredLD = MutableLiveData(false)

    private val networkManager: NetworkManager
    private val compositeDisposable = CompositeDisposable()

    init {
        networkManager = App.getInstance(application.applicationContext).networkManager
    }

    fun registration(password: String, rePassword: String, username: String, email: String) {

        inputErrorsLD.value = checkErrorsInput(password, rePassword, username, email)

        if (inputErrorsLD.value?.isNotEmpty() == true) return

        val disposable = networkManager.registration(password, username, email)
            .doOnSubscribe { processRegistrationLD.postValue(true) }
            .doFinally { processRegistrationLD.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { registeredLD.value = true },
                { errorLD.value = true }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun checkErrorsInput(
        password: String,
        rePassword: String,
        username: String,
        email: String
    ): List<InputError> {
        val errorList = mutableListOf<InputError>()
        if (!email.contains("@")) errorList.add(InputError.EmailError)
        if (password != rePassword) errorList.add(InputError.PasswordsNotEquals)
        if (username.length <= 2) errorList.add(InputError.UserNameError)
        if (password.length <= 2) errorList.add(InputError.PasswordError)
        return errorList
    }

}