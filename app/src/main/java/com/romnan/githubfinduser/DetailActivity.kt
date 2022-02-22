package com.romnan.githubfinduser

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.romnan.githubfinduser.model.User

class DetailActivity : AppCompatActivity() {
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        user = intent.extras?.getParcelable(EXTRA_USER)
        user?.let { populateUserDetail(it) }
    }

    private fun populateUserDetail(user: User) {
        val ivProfilePicture = findViewById<ImageView>(R.id.iv_profile_picture)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvLocation = findViewById<TextView>(R.id.tv_location)
        val tvCompany = findViewById<TextView>(R.id.tv_company)
        val tvRepositories = findViewById<TextView>(R.id.tv_repositories)
        val tvFollowers = findViewById<TextView>(R.id.tv_followers)
        val tvFollowing = findViewById<TextView>(R.id.tv_following)

        supportActionBar?.title = user.username
        Glide.with(this)
            .load(user.avatar)
            .circleCrop()
            .into(ivProfilePicture)
        tvName.text = user.name
        tvUsername.text = user.username
        tvLocation.text = user.location
        tvCompany.text = user.company
        tvRepositories.text = user.repositories
        tvFollowers.text = user.followers
        tvFollowing.text = user.following
    }

    private fun startShareIntent(user: User) {
        val content = String.format(getString(R.string.share_content), user.name, user.username)
        val intent = Intent(Intent.ACTION_SEND).apply {
            this.type = "text/plain"
            this.putExtra(Intent.EXTRA_TEXT, content)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_chooser_message)))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_share) user?.let { startShareIntent(it) }
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}