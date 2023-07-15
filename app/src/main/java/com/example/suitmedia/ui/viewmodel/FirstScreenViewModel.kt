package com.example.suitmedia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitmedia.data.database.UserPreferences
import kotlinx.coroutines.launch

class FirstScreenViewModel(private val pref: UserPreferences) : ViewModel() {
    fun saveUser(userName: String) {
         viewModelScope.launch {
            pref.saveUser(userName)
        }
    }
}