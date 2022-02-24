package com.romnan.githubfinduser.feature_user_detail.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.romnan.githubfinduser.feature_user_detail.presentation.follower_list.FollowersListFragment
import com.romnan.githubfinduser.feature_user_detail.presentation.following_list.FollowingListFragment


class FollowersFollowingAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> FollowersListFragment()
            1 -> FollowingListFragment()
            else -> Fragment()
        }
        return fragment
    }
}