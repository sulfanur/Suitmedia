package com.example.suitmedia.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmedia.data.database.UserPreferences
import com.example.suitmedia.databinding.ActivityThirdScreenBinding
import com.example.suitmedia.ui.viewmodel.ThirdScreenViewModel
import com.example.suitmedia.ui.viewmodel.ViewModelFactory
import com.example.suitmediaapp.adapter.LoadingAdapter
import com.example.suitmediaapp.adapter.UserAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("DEPRECATION")
class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var thirdViewModel: ThirdScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        thirdViewModel = ViewModelProvider(
            this@ThirdScreen,
            ViewModelFactory(UserPreferences.getInstance(dataStore), this@ThirdScreen)
        )[ThirdScreenViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        initAction()
        getData()
    }
    private fun initAction() {
        binding.apply {
            thirdToolbar.setNavigationOnClickListener {
                onBackPressed()
                finish()
            }
            thirdSwipeLayout.setOnRefreshListener {
                getData()
                thirdSwipeLayout.isRefreshing = false
            }
        }
    }

    private fun getData() {
        val adapter = UserAdapter { String ->
            thirdViewModel.selectedUser(String)
            finish()
        }
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && adapter.itemCount < 1) {
                binding.apply {
                    tvEmpty.isVisible = true
                    rvUsers.isVisible = false
                }
            } else {
                binding.apply {
                    tvEmpty.isVisible = false
                    rvUsers.isVisible = true
                }
            }
        }
        binding.rvUsers.adapter = adapter.withLoadStateFooter(
            footer = LoadingAdapter {
                adapter.retry()
            }
        )
        thirdViewModel.users.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
