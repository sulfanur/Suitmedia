package com.example.suitmedia.data.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val SELECTED_USERNAME_KEY = stringPreferencesKey("selected")

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[USERNAME_KEY] ?: "",
                preferences[SELECTED_USERNAME_KEY] ?: "Selected User Name"
            )
        }
    }

    suspend fun saveUser(selectedUserName: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = selectedUserName
        }
    }
    suspend fun selectedUser(selectedUserName: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_USERNAME_KEY] = selectedUserName
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}