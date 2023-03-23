package org.telnyx.androidclient.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PrefManager(context: Context) {

    private val CREDENTIALS_KEY = "TELNYX.APP.ANDROID.CREDENTIALS_KEY"

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    //encrypt for production apps
    var userCredential: UserCredential?
        get() {
            return getSharedPrefObject<UserCredential>(CREDENTIALS_KEY)
        }
        set(value) {
            saveToSharedPref(CREDENTIALS_KEY,value)
        }

    private inline fun <reified T> saveToSharedPref(key: String, objectValue: T) {
        val serializedObject = Json.encodeToString(objectValue)
        saveSharedPrefString(key, serializedObject)
    }

    private fun getSharedPrefString(key: String): String {
        return sharedPreferences
            .getString(key, "") ?: ""
    }

    private fun saveSharedPrefString(key: String,value:String) {
         sharedPreferences.edit().putString(key, value).apply()
    }

    private inline fun <reified T> getSharedPrefObject(
        preferenceKey: String,
    ): T? {
        return try {
            val cache = getSharedPrefString(preferenceKey)
            if (cache.isEmpty()) return null
            Json.decodeFromString(cache)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}