package com.app.proofofconcept.ui.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.app.proofofconcept.BR
import com.app.proofofconcept.R
import com.app.proofofconcept.ViewModelProviderFactory
import com.app.proofofconcept.databinding.ActivityHomeBinding
import com.app.proofofconcept.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_home


    override fun getViewModel(): HomeViewModel? {
        return ViewModelProviders.of(this, ViewModelProviderFactory(getDataManager())).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()?.loadData()
    }
}
