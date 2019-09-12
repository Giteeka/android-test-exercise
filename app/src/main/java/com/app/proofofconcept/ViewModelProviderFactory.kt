package com.app.proofofconcept

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.proofofconcept.data.DataManager
import com.app.proofofconcept.ui.home.HomeViewModel

class ViewModelProviderFactory constructor(var dataManager: DataManager) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataManager) as T
        }
        return super.create(modelClass)
    }
}