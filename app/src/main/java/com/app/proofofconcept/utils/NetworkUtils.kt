package com.app.proofofconcept.utils

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtils(var context: Context) {

    fun isNetworkConnected(): Boolean {
        val cm =  context.getSystemService (Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }
}
