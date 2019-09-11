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
import com.app.proofofconcept.data.local.SharedPreferenceHelper
import com.app.proofofconcept.data.model.RowItem
import com.app.proofofconcept.databinding.ActivityHomeBinding
import com.app.proofofconcept.ui.base.BaseActivity
import com.app.proofofconcept.utils.NetworkUtils
import com.google.android.material.snackbar.Snackbar


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {
    override fun noInternetConnection() {
        showToast(getString(R.string.no_internet_connection))
    }

    override fun showToast(s: String) {
        viewDataBinding?.root?.let { Snackbar.make(it, s, Snackbar.LENGTH_LONG).show() }
    }

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
            getViewModel()?.loadData(true)
        }
        getViewModel()?.setNavigator(this)
        supportActionBar?.title =
            getDataManager().sharePref.getString(SharedPreferenceHelper.PREF_TITLE, getString(R.string.title_home))
        getViewModel()?.loadData()

        getViewModel()?.listLiveData?.observe(this,
            Observer<List<RowItem>> { t ->
                supportActionBar?.title =
                    getDataManager().sharePref.getString(
                        SharedPreferenceHelper.PREF_TITLE,
                        getString(R.string.title_home)
                    )
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
                getViewModel()?.loadData(true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
