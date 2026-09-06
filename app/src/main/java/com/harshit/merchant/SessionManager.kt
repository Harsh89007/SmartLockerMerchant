package com.harshit.merchant

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "SmartMerchantPrefs"
    private const val KEY_MPIN = "merchant_mpin"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveMpin(context: Context, mpin: String) {
        getPrefs(context).edit().putString(KEY_MPIN, mpin).apply()
    }

    fun getMpin(context: Context): String? {
        return getPrefs(context).getString(KEY_MPIN, null)
    }
}
