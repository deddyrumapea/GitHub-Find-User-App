package com.romnan.githubfinduser.feature_search_user.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.core.presentation.PreferencesEvent
import com.romnan.githubfinduser.core.presentation.PreferencesViewModel
import com.romnan.githubfinduser.feature_fav_users_list.presentation.FavUsersListActivity
import com.romnan.githubfinduser.feature_search_user.presentation.adapter.UserAdapter
import com.romnan.githubfinduser.feature_user_detail.presentation.UserDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUsersActivity : AppCompatActivity() {
    private val viewModel: SearchUsersViewModel by viewModels()
    private val prefViewModel: PreferencesViewModel by viewModels()

    private var rvUsersList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_search_users)

        prefViewModel.state.observe(this) {
            AppCompatDelegate.setDefaultNightMode(
                if (it.isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        rvUsersList = findViewById(R.id.rv_users_list)
        val tvNoUsers = findViewById<TextView>(R.id.tv_no_users)
        val svUser = findViewById<SearchView>(R.id.sv_user)

        val userAdapter = UserAdapter()

        userAdapter.onItemClick = { selectedUser ->
            val intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_USERNAME, selectedUser.username)
            startActivity(intent)
        }

        rvUsersList?.apply {
            this.layoutManager = LinearLayoutManager(this@SearchUsersActivity)
            this.adapter = userAdapter
        }

        viewModel.searchQuery.observe(this) { svUser.setQuery(it, false) }

        viewModel.searchUsersState.observe(this) {
            userAdapter.setUsersList(it.usersList)
            showProgressBar(it.isLoading)

            tvNoUsers.visibility =
                if (it.usersList.isEmpty() && !it.isLoading) View.VISIBLE else View.GONE

            if (it.errorMessage.isNotBlank()) {
                Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.onEvent(SearchUsersEvent.QueryTextSubmit(it)) }

                return true
            }
        })
    }

    private fun showProgressBar(loading: Boolean) {
        val pbUsersList = findViewById<ProgressBar>(R.id.pb_users_list)
        if (loading) {
            pbUsersList.visibility = View.VISIBLE
            rvUsersList?.visibility = View.INVISIBLE
        } else {
            rvUsersList?.visibility = View.VISIBLE
            pbUsersList.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_user, menu)

        val miDarkMode = menu.findItem(R.id.mi_dark_mode)

        prefViewModel.state.observe(this) {
            miDarkMode.isChecked = it.isDarkModeEnabled
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_fav_users -> startActivity(
                Intent(
                    this@SearchUsersActivity,
                    FavUsersListActivity::class.java
                )
            )

            R.id.mi_dark_mode -> {
                prefViewModel.onEvent(PreferencesEvent.ToggleDarkTheme)
            }
        }
        return true
    }
}