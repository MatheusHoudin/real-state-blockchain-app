package com.example.realstateblockchainapp.shared.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "realstatenft")

interface PreferencesRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
}

class PreferencesRepositoryImpl(
    private val context: Context
) : PreferencesRepository {
    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

}

const val PRIVATE_WALLET_KEY = "private_wallet_key"
const val USER_EMAIL_KEY = "user_email_key"
const val USER_NAME_KEY = "user_name_key"