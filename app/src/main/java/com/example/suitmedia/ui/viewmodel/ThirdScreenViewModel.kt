package com.example.suitmedia.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitmedia.data.database.UserPreferences
import com.example.suitmedia.data.repository.UserRepository
import com.example.suitmediaapp.data.UserResponseItem
import kotlinx.coroutines.launch

class ThirdScreenViewModel(private val pref: UserPreferences, userRepository: UserRepository) :
    ViewModel() {
    val users: LiveData<PagingData<UserResponseItem>> =
        userRepository.getUser().cachedIn(viewModelScope)

    fun selectedUser(selectedUserName: String) {
        viewModelScope.launch {
            pref.selectedUser(selectedUserName)
        }
    }
}