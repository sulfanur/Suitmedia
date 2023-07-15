package com.example.suitmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.suitmedia.data.database.User
import com.example.suitmedia.data.database.UserPreferences

class SecondScreenViewModel(private val pref: UserPreferences) : ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}