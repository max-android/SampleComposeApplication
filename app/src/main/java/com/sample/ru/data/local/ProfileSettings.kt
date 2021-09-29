package com.sample.ru.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.sample.ru.util.EMPTY_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ProfileSettings(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PROFILE_STORE
    )
    private val DARK_ENABLED = booleanPreferencesKey("enabled_dark")
    private val USER_NAME = stringPreferencesKey("user_name")

    val themeSwitch: Flow<Boolean> = context.dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[DARK_ENABLED] ?: false
        }

    suspend fun setEnabledSwitch(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_ENABLED] = enabled
        }
    }

    val userName: Flow<String> = context.dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[USER_NAME] ?: EMPTY_VALUE
        }

    suspend fun setUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    companion object {
        private const val PROFILE_STORE = "profile_store"
    }
}