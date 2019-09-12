package com.app.proofofconcept.ui.home

/**
 *
 * Created by admin on 9/7/2019.
 */
interface HomeNavigator {
    /**
     * show info/error message
     * @param s String message text
     */
    fun showToast(s: String)

    /**
     * show no internet connection message
     */
    fun noInternetConnection()
}