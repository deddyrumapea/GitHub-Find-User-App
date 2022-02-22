package com.romnan.githubfinduser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romnan.githubfinduser.adapter.UserAdapter
import com.romnan.githubfinduser.model.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val userAdapter = UserAdapter(getUsersList())
        userAdapter.onItemClick = { selectedUser ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, selectedUser)
            startActivity(intent)
        }

        val rvUser = findViewById<RecyclerView>(R.id.rv_user)
        with(rvUser) {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun getUsersList(): List<User> {
        val avatars = resources.obtainTypedArray(R.array.avatar)
        val names = resources.getStringArray(R.array.name)
        val usernames = resources.getStringArray(R.array.username)
        val locations = resources.getStringArray(R.array.location)
        val companies = resources.getStringArray(R.array.company)
        val repositories = resources.getStringArray(R.array.repository)
        val followers = resources.getStringArray(R.array.followers)
        val followings = resources.getStringArray(R.array.following)

        val usersList = ArrayList<User>()

        for (i in 0..names.lastIndex) {
            val user = User(
                avatar = avatars.getResourceId(i, -1),
                name = names[i],
                username = usernames[i],
                location = locations[i],
                company = companies[i],
                repositories = repositories[i],
                followers = followers[i],
                following = followings[i]
            )
            usersList.add(user)
        }
        avatars.recycle()

        return usersList
    }
}