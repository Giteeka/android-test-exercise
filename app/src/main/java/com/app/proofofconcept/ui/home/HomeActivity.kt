package com.app.proofofconcept.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.test.espresso.IdlingResource
import com.app.proofofconcept.BR
import com.app.proofofconcept.R
import com.app.proofofconcept.ViewModelProviderFactory
import com.app.proofofconcept.data.local.SharedPreferenceHelper
import com.app.proofofconcept.data.model.RowItem
import com.app.proofofconcept.databinding.ActivityHomeBinding
import com.app.proofofconcept.ui.base.BaseActivity
import com.app.proofofconcept.utils.SimpleIdlingResource
import com.google.android.material.snackbar.Snackbar


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {

    private var mIdlingResource: SimpleIdlingResource? = null


    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_home

    override fun getViewModel(): HomeViewModel? {
        return ViewModelProviders.of(this, ViewModelProviderFactory(getDataManager())).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set navigator for view model
        getViewModel()?.setNavigator(this)

        // set title of screen when json data is not stored default title would be "About"
        supportActionBar?.title =
            getDataManager().sharePref.getString(SharedPreferenceHelper.PREF_TITLE, getString(R.string.title_home))

        // this is used for ui test cases
        mIdlingResource?.setIdleState(false)

        getViewModel()?.loadData()

        getViewModel()?.listLiveData?.observe(this,
            Observer<List<RowItem>> { t ->
                mIdlingResource?.setIdleState(true)
                supportActionBar?.title =
                    getDataManager().sharePref.getString(
                        SharedPreferenceHelper.PREF_TITLE,
                        getString(R.string.title_home)
                    )
                viewDataBinding?.rvItems?.adapter = HomeAdapter(t)
            })

        //handles pull to refresh of list screen
        viewDataBinding?.swipeToRefresh?.setOnRefreshListener {
            getViewModel()?.loadData(true)
        }

    }

    /**
     * Add menu items to actionbar
     *
     * @param menu Menu
     * @return Boolean
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    /**
     * handle clicks of menu item
     * @param item MenuItem
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.id_refresh -> {
                getViewModel()?.loadData(true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun noInternetConnection() {
        showToast(getString(R.string.no_internet_connection))
    }

    override fun showToast(s: String) {
        viewDataBinding?.root?.let { Snackbar.make(it, s, Snackbar.LENGTH_LONG).show() }
    }

    /**
     * Only called from test, creates and returns a new [SimpleIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource!!
    }
}
