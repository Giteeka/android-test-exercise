package com.app.proofofconcept.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.proofofconcept.data.DataManager
import com.app.proofofconcept.data.local.SharedPreferenceHelper
import com.app.proofofconcept.data.model.RowItem
import com.app.proofofconcept.data.network.reponse.FactResponse
import com.app.proofofconcept.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private var dataManager: DataManager) : BaseViewModel<HomeNavigator>() {

    private val TAG = "HomeViewModel"

    /**
     * this is live data of list view which will be coming either from network or localdata
     * firstly it will be fetch from network ,stored in database and nexttime it will be fetched from database
     */
    var listLiveData: MutableLiveData<List<RowItem>> = MutableLiveData()

    /**
     * when 0 datas found from network/local
     * handles no dataview biniding
     */
    var noDataFound: ObservableField<Boolean> = ObservableField(false)


    /**
     * load data from network/local
     * @param fromNetwork Boolean is true when we want data from network without
     * checking in local storage
     *
     */
    fun loadData(fromNetwork: Boolean = false) {

        viewModelScope.launch {
            listLiveData.value = dataManager.rowDao.getRowItems()
            val list = listLiveData.value
            if (list?.isNotEmpty() == true && !fromNetwork) {
                // load data from local
                loadDataFromLocal()
            } else {
                // load data remotely
                callDataFromNetwork()
            }
        }

    }

    /**
     * Using API call data from network store to database and update live data
     * on failure case load data from database
     * save title to store in shared preference
     */
    private fun callDataFromNetwork() {

        if (!dataManager.networkUtils.isNetworkConnected()) {
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
                    dataManager.sharePref.setString(SharedPreferenceHelper.PREF_TITLE, title)
                    if ((rows?.size ?: 0) > 0) {
                        val launch = viewModelScope.launch {
                            dataManager.rowDao.deleteAll()
                            noDataFound.set(false)
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

    /**
     *  load data from database
     */
    private fun loadDataFromLocal() {
        viewModelScope.launch {
            listLiveData.value = dataManager.rowDao.getRowItems()
            noDataFound.set(listLiveData.value?.size ?: 0 == 0)
        }
    }

}