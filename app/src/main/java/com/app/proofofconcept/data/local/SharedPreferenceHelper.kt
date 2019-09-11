package com.app.proofofconcept.data.local

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * Created by admin on 9/7/2019.
 */
class SharedPreferenceHelper {


    companion object {
        const val PREF_TITLE = "prefTitle"
        private var pref: SharedPreferenceHelper? = null
        fun getInstance(context: Context): SharedPreferenceHelper {
            if (pref == null) {
                pref = SharedPreferenceHelper()
                pref?.setPref(context, "pref-storage")
            }
            return pref!!
        }
    }

    private lateinit var mPrefs: SharedPreferences

    fun setPref(context: Context, prefFileName: String) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    fun getString(key: String, defaultValue: String? = ""): String? {
        return mPrefs.getString(key, defaultValue)
    }

    fun setString(key: String, value: String?) {
        mPrefs.edit().putString(key, value).apply()
    }

    fun getInt(key: String): Int {
        return mPrefs.getInt(key, -1)
    }

    fun setInt(key: String, value: Int) {
        mPrefs.edit().putInt(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return mPrefs.getBoolean(key, false)
    }

    fun setBoolean(key: String, value: Boolean) {
        mPrefs.edit().putBoolean(key, value).apply()
    }
}