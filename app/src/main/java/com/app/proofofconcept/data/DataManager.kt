package com.app.proofofconcept.data

import android.content.Context
import com.app.proofofconcept.data.local.RoomDatabaseHelper
import com.app.proofofconcept.data.local.RowDao
import com.app.proofofconcept.data.local.SharedPreferenceHelper
import com.app.proofofconcept.data.network.ApiHelper
import com.app.proofofconcept.utils.NetworkUtils

/**
 *
 * Created by admin on 9/7/2019.
 */

class DataManager() {

    lateinit var rowDao: RowDao
    lateinit var sharePref: SharedPreferenceHelper
    lateinit var apiHelper: ApiHelper
    lateinit var networkUtils: NetworkUtils

    constructor(context: Context) : this() {
        rowDao = RoomDatabaseHelper.getInstance(context).rowDao()
        sharePref = SharedPreferenceHelper.getInstance(context)
        apiHelper = ApiHelper
        networkUtils = NetworkUtils(context)
    }

}