package com.sunaa.spendmuch.timecounter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val USER_TIME = longPreferencesKey("user_time")

class PrefImplementaion @Inject constructor(
    val dataStore: DataStore<Preferences>
) {
    fun getUserTime(): Flow<Long> {
        return dataStore.data.catch { emit(emptyPreferences()) }.map {
            it[USER_TIME] ?: 0L
        }
    }

    suspend fun saveNewTime(newTotal: Long) {
        dataStore.edit { preferences ->
            preferences[USER_TIME] = newTotal
        }
    }

    suspend fun clearSavedTime(){
        dataStore.edit {preferences ->
            preferences.remove(USER_TIME)
        }
    }

}