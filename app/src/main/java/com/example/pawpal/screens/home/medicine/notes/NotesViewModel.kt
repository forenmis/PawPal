package com.example.pawpal.screens.home.medicine.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.screens.home.medicine.entity.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val databaseManager = App.getInstance(application.applicationContext).databaseManager

    val noteListLD = MutableLiveData<List<Note>>()

    init {
        loadNotesList()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun loadNotesList() {
        val disposable =
            databaseManager.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ noteListLD.postValue(it) }, {})
        compositeDisposable.add(disposable)
    }
}