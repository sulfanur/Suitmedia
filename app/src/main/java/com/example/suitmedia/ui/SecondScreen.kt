package com.example.suitmedia.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.suitmedia.data.database.UserPreferences
import com.example.suitmedia.databinding.ActivitySecondScreenBinding
import com.example.suitmedia.ui.viewmodel.SecondScreenViewModel
import com.example.suitmedia.ui.viewmodel.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("DEPRECATION")
class SecondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var secondViewModel: SecondScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        secondViewModel = ViewModelProvider(this, ViewModelFactory(UserPreferences.getInstance(dataStore), this)
        )[SecondScreenViewModel::class.java]

        secondViewModel.getUser().observe(this) { user ->
            binding.apply {
                tvName.text = user.userName
                tvSelected.text = user.selectedUserName
            }
        }

        initAction()
    }

    private fun initAction() {
        binding.apply {
            secondToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            btnChoose.setOnClickListener {
                startThirdScreen()
            }
        }
    }

    private fun startThirdScreen() {
        startActivity(Intent(this, ThirdScreen::class.java))
    }
}
