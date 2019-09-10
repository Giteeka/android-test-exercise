package com.app.proofofconcept.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.proofofconcept.data.DataManager
import com.app.proofofconcept.data.model.RowItem
import com.app.proofofconcept.ui.base.BaseViewModel
import com.app.proofofconcept.utils.Logg
import com.bumptech.glide.load.HttpException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(private var dataManager: DataManager) : BaseViewModel<HomeNavigator>() {

    private val TAG = "HomeViewModel"

    var listLiveData: MutableLiveData<List<RowItem>> = MutableLiveData()

    var noDataFound: ObservableField<Boolean> = ObservableField(false)
    fun loadData() {
        viewModelScope.launch {
            listLiveData.value = dataManager.rowDao.getRowItems()
            Logg.e(TAG, "list size : ${listLiveData.value?.size}")
            val list = listLiveData.value
            if (list?.isNotEmpty() == true) {
                // load data from local
                loadDataFromLocal()
            } else {
                // load data remotely
                callDataFromNetwork()
            }
        }

    }

    private fun callDataFromNetwork() {
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val response = dataManager.apiHelper.getFacts()
            withContext(Dispatchers.Main) {
                try {
                    isLoading.set(false)
                    if (response.isSuccessful) {
                        // insert data in local
                        val rows = response.body()?.rows
                        val title = response.body()?.title
                        Logg.e(TAG, "title : $title")
                        if ((rows?.size ?: 0) > 0) {
                            noDataFound.set(false)
                            dataManager.rowDao.insert(rows!!)
                            loadDataFromLocal()
                        } else {
                            noDataFound.set(true)
                        }
                    } else {
                        getNavigator()?.showToast("Error: ${response.code()}")
                    }
                } catch (e: HttpException) {
                    isLoading.set(false)
                    getNavigator()?.showToast("Exception: ${e.message}")
                } catch (e: Throwable) {
                    isLoading.set(false)
                    getNavigator()?.showToast("Oops: Something else went wrong")
                }
            }
        }
    }

    private fun loadDataFromLocal() {
        viewModelScope.launch {
            listLiveData.value = dataManager.rowDao.getRowItems()
            Logg.e(TAG, "list size : ${listLiveData.value?.size}")
        }
    }

}