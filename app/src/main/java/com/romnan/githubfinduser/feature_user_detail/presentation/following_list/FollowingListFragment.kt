package com.romnan.githubfinduser.feature_user_detail.presentation.following_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.feature_user_detail.presentation.UserDetailActivity
import com.romnan.githubfinduser.feature_user_detail.presentation.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingListFragment : Fragment() {
    private val viewModel: UserDetailViewModel by activityViewModels()
    private var pbUsersList: ProgressBar? = null
    private var rvUsersList: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvNoUsers = view.findViewById<TextView>(R.id.tv_no_users)
        pbUsersList = view.findViewById(R.id.pb_users_list)
        rvUsersList = view.findViewById(R.id.rv_users_list)

        val followingAdapter = FollowingAdapter()
        followingAdapter.onItemClick = { selectedUser ->
            val intent = Intent(requireActivity(), UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.EXTRA_USERNAME, selectedUser.username)
            startActivity(intent)
        }

        rvUsersList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = followingAdapter
        }

        viewModel.followingListState.observe(viewLifecycleOwner) {
            followingAdapter.setFollowingList(it.usersList)
            showProgressBar(it.isLoading)

            tvNoUsers.visibility =
                if (it.usersList.isEmpty() && !it.isLoading) View.VISIBLE else View.GONE

            if (it.errorMessage.isNotBlank()) {
                Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showProgressBar(loading: Boolean) {
        if (loading) {
            pbUsersList?.visibility = View.VISIBLE
            rvUsersList?.visibility = View.INVISIBLE
        } else {
            rvUsersList?.visibility = View.VISIBLE
            pbUsersList?.visibility = View.INVISIBLE
        }
    }
}