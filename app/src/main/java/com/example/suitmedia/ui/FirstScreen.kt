package com.example.suitmedia.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.suitmedia.R
import com.example.suitmedia.data.database.UserPreferences
import com.example.suitmedia.databinding.ActivityFirstScreenBinding
import com.example.suitmedia.ui.viewmodel.FirstScreenViewModel
import com.example.suitmedia.ui.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FirstScreen : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding
    private lateinit var firstViewModel: FirstScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firstViewModel = ViewModelProvider(this, ViewModelFactory(UserPreferences.getInstance(dataStore), this)
        )[FirstScreenViewModel::class.java]

        initAction()
    }

    private fun initAction() {
        binding.apply {
            btnCheck.setOnClickListener {
                val palindromeText = edtPalindrome.text.toString().replace("\\s".toRegex(), "")
                if (palindromeText.isEmpty()) {
                    showDialog("Invalid", getString(R.string.input_palindrome))
                } else {
                    if (isPalindrome(palindromeText)) {
                        showDialog("Result", getString(R.string.is_palindrome))
                    } else {
                        showDialog("Result", getString(R.string.not_palindrome))
                    }
                }
            }
            btnNext.setOnClickListener {
                val name = edtName.text.toString()
                if (name.isEmpty()) {
                    showDialog("Invalid", getString(R.string.name_input))
                } else {
                    startSecondScreen(name)
                }
            }
        }
    }
    private fun showDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun isPalindrome(text: String): Boolean {
        val reverseString = text.reversed()
        return text.equals(reverseString, ignoreCase = true)
    }

    private fun startSecondScreen(name: String) {
        firstViewModel.saveUser(name)
        startActivity(Intent(this, SecondScreen::class.java))
    }

}
