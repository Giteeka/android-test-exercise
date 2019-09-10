package com.app.proofofconcept.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.proofofconcept.BR
import com.app.proofofconcept.R
import com.app.proofofconcept.ViewModelProviderFactory
import com.app.proofofconcept.data.model.RowItem
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
        viewDataBinding?.swipeToRefresh?.setOnRefreshListener {
            getViewModel()?.callDataFromNetwork()
        }
        getViewModel()?.loadData()
        getViewModel()?.listLiveData?.observe(this,
            Observer<List<RowItem>> { t ->
                viewDataBinding?.rvItems?.adapter = HomeAdapter(t)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.id_refresh -> {
                getViewModel()?.callDataFromNetwork()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
