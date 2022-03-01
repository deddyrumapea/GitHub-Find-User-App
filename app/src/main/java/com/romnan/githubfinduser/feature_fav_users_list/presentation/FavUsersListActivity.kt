package com.romnan.githubfinduser.feature_fav_users_list.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.feature_search_user.presentation.adapter.UserAdapter
import com.romnan.githubfinduser.feature_user_detail.presentation.UserDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavUsersListActivity : AppCompatActivity() {
    private val viewModel: FavUsersListViewModel by viewModels()

    private var rvUsersList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_users_list)

        supportActionBar?.title = getString(R.string.favorite_users)

        rvUsersList = findViewById(R.id.rv_users_list)
        val tvNoUsers = findViewById<TextView>(R.id.tv_no_users)

        val userAdapter = UserAdapter()

        userAdapter.onItemClick = { selectedUser ->
            val intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_USERNAME, selectedUser.username)
            startActivity(intent)
        }

        rvUsersList?.apply {
            this.layoutManager = LinearLayoutManager(this@FavUsersListActivity)
            this.adapter = userAdapter
        }

        viewModel.favUsersListState.observe(this) {
            userAdapter.setUsersList(it.usersList)
            showProgressBar(it.isLoading)

            tvNoUsers.visibility =
                if (it.usersList.isEmpty() && !it.isLoading) View.VISIBLE else View.GONE

            if (it.errorMessage.isNotBlank()) {
                Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(FavUsersListEvent.ActivityResume)
    }
}