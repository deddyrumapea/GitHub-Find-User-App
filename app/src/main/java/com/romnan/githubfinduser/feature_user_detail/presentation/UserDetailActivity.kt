package com.romnan.githubfinduser.feature_user_detail.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.feature_user_detail.domain.model.UserDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private val viewModel: UserDetailViewModel by viewModels()

    private var userDetail: UserDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        username?.let { viewModel.onEvent(UserDetailEvent.UsernameReceived(it)) }

        val vpFoll = findViewById<ViewPager2>(R.id.vp_followers_following)
        val tlFoll = findViewById<TabLayout>(R.id.tl_followers_following)
        val tbFav = findViewById<ToggleButton>(R.id.tb_fav)

        val follAdapter = FollowersFollowingAdapter(this)
        vpFoll.adapter = follAdapter

        tbFav.setOnClickListener { viewModel.onEvent(UserDetailEvent.ToggleFav) }

        TabLayoutMediator(tlFoll, vpFoll) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.followers)
                1 -> getString(R.string.following)
                else -> ""
            }
        }.attach()

        viewModel.userDetailState.observe(this) { state ->
            state.userDetail?.let {
                userDetail = it
                populateUserDetail(it)
            }
            showProgressBar(state.isLoading)
            if (state.errorMessage.isNotBlank()) Toast.makeText(
                this,
                state.errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        viewModel.isFav.observe(this) { tbFav.isChecked = it }
    }

    private fun showProgressBar(visible: Boolean) {
        val pbUserDetail = findViewById<ProgressBar>(R.id.pb_user_detail)
        pbUserDetail.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun populateUserDetail(user: UserDetail) {
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
            .load(user.avatarUrl)
            .circleCrop()
            .into(ivProfilePicture)

        if (user.name.isNotBlank()) tvName.text = user.name else tvName.visibility = View.INVISIBLE

        tvUsername.text = user.username

        if (user.location.isNotBlank()) tvLocation.text = user.location
        else tvLocation.visibility = View.GONE

        if (user.company.isNotBlank()) tvCompany.text = user.company
        else tvCompany.visibility = View.GONE

        tvRepositories.text = user.repositoriesCount.toString()
        tvFollowers.text = user.followersCount.toString()
        tvFollowing.text = user.followingCount.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user_detail, menu)
        return true
    }

    private fun startShareIntent(user: UserDetail) {
        val content = String.format(getString(R.string.share_content), user.username, user.username)
        val intent = Intent(Intent.ACTION_SEND).apply {
            this.type = "text/plain"
            this.putExtra(Intent.EXTRA_TEXT, content)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_chooser_message)))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_share -> userDetail?.let { startShareIntent(it) }
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}