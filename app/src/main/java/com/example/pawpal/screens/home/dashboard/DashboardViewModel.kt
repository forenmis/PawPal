package com.example.pawpal.screens.home.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.app.App
import com.example.pawpal.data.network.NetworkManager
import com.example.pawpal.entity.Banner
import com.example.pawpal.entity.Item
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val networkManager: NetworkManager
    private val compositeDisposable = CompositeDisposable()

    val toysLD = MutableLiveData<List<Item>>()
    val foodLD = MutableLiveData<List<Item>>()
    val processLD = MutableLiveData(true)
    val bannerLD = MutableLiveData<Banner>()

    init {
        networkManager = App.getInstance(application.applicationContext).networkManager
        loadDashboard()
        loadBanner()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun loadDashboard() {
        val disposable = Single.zip(
            networkManager.getFood(),
            networkManager.getToys()
        ) { food, toys -> food to toys }
            .map { (food, toys) ->
                val foodItems = toItems(food) { Item.FoodItem(it) }
                val toysItems = toItems(toys) { Item.ToyItem(it) }
                foodItems to toysItems
            }
            .doOnSubscribe { processLD.postValue(true) }
            .delay(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { processLD.postValue(false) }
            .subscribe { (food, toys) ->
                toysLD.postValue(toys)
                foodLD.postValue(food)
            }
        compositeDisposable.add(disposable)
    }

    private fun loadBanner() {
        val disposable = networkManager.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { banner -> bannerLD.postValue(banner) }
        compositeDisposable.add(disposable)
    }

    private fun <T> toItems(items: List<T>, mapper: (T) -> Item): List<Item> {
        return if (items.size <= MAX_COUNT) {
            items.map(mapper)
        } else {
            buildList {
                addAll(items.take(MAX_COUNT).map(mapper))
                add(Item.SeeAll)
            }
        }
    }

    companion object {
        private const val MAX_COUNT = 3
    }
}