package com.example.suitmedia.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suitmedia.data.database.UserPreferences
import com.example.suitmediaapp.di.Injection

class ViewModelFactory(private val pref: UserPreferences, private val context: Context) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FirstScreenViewModel::class.java) -> {
                FirstScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SecondScreenViewModel::class.java) -> {
                SecondScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ThirdScreenViewModel::class.java) -> {
                ThirdScreenViewModel(pref, Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
}