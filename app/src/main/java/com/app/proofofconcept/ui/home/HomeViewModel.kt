package com.app.proofofconcept.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.proofofconcept.data.DataManager
import com.app.proofofconcept.data.local.SharedPreferenceHelper
import com.app.proofofconcept.data.model.RowItem
import com.app.proofofconcept.data.network.reponse.FactResponse
import com.app.proofofconcept.ui.base.BaseViewModel
import com.app.proofofconcept.utils.Logg
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private var dataManager: DataManager) : BaseViewModel<HomeNavigator>() {

    private val TAG = "HomeViewModel"

    var listLiveData: MutableLiveData<List<RowItem>> = MutableLiveData()

    var noDataFound: ObservableField<Boolean> = ObservableField(false)
    fun loadData(isForced: Boolean = false) {
        viewModelScope.launch {
            listLiveData.value = dataManager.rowDao.getRowItems()
            Logg.e(TAG, "list size : ${listLiveData.value?.size}")
            val list = listLiveData.value
            if (list?.isNotEmpty() == true && !isForced) {
                // load data from local
                loadDataFromLocal()
            } else {
                // load data remotely
                callDataFromNetwork()
            }
        }

    }

    private fun callDataFromNetwork() {

        if (!dataManager?.networkUtils?.isNetworkConnected()) {
            getNavigator()?.noInternetConnection()
            loadDataFromLocal()
            return
        }

        isLoading.set(true)
        val response = dataManager.apiHelper.getFacts()

        response.enqueue(object : Callback<FactResponse> {
            override fun onFailure(call: Call<FactResponse>, t: Throwable) {
                isLoading.set(false)
                loadDataFromLocal()
                getNavigator()?.showToast("Exception: ${t.message}")
            }

            override fun onResponse(call: Call<FactResponse>, response: Response<FactResponse>) {
                isLoading.set(false)
                if (response.isSuccessful) {
                    // insert data in local
                    val rows = response.body()?.rows
                    val title = response.body()?.title
                    Logg.e(TAG, "title : $title")
                    dataManager.sharePref.setString(SharedPreferenceHelper.PREF_TITLE, title)
                    if ((rows?.size ?: 0) > 0) {
                        val launch = viewModelScope.launch {
                            dataManager.rowDao.deleteAll()
                            listLiveData.value = rows
                            dataManager.rowDao.insert(rows!!)
                        }
                    } else {
                        loadDataFromLocal()
                    }
                } else {
                    loadDataFromLocal()
                    getNavigator()?.showToast("Error: ${response.code()}")
                }
            }

        })
    }

    private fun loadDataFromLocal() {
        Logg.e(TAG, "loadDataFromLocal: ")
        viewModelScope.launch {
            listLiveData.value = dataManager.rowDao.getRowItems()
            Logg.e(TAG, "list size------------ : ${listLiveData.value?.size}")
            noDataFound.set(listLiveData.value?.size ?: 0 == 0)
        }
    }

}