package com.romnan.githubfinduser.feature_user_detail.presentation.follower_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowerUser

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowerViewHolder>() {
    private val followersList = ArrayList<FollowerUser>()
    var onItemClick: ((FollowerUser) -> Unit)? = null

    fun setFollowersList(newFollowersList: List<FollowerUser>) {
        val diffResult = DiffUtil.calculateDiff(
            FollowerItemDiffCallback(oldItems = followersList, newItems = newFollowersList)
        )
        followersList.clear()
        followersList.addAll(newFollowersList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FollowerItemDiffCallback(
        val oldItems: List<FollowerUser>,
        val newItems: List<FollowerUser>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].username == newItems[newItemPosition].username

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(followersList[adapterPosition])
            }
        }

        fun bind(follower: FollowerUser) {
            val ivProfilePicture = itemView.findViewById<ImageView>(R.id.iv_profile_picture)
            val tvUsername = itemView.findViewById<TextView>(R.id.tv_username)
            val tvType = itemView.findViewById<TextView>(R.id.tv_type)

            Glide.with(itemView)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(ivProfilePicture)
            tvUsername.text = follower.username
            tvType.text = follower.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder =
        FollowerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )


    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(followersList[position])
    }

    override fun getItemCount(): Int = followersList.size
}