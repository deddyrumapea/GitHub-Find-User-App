package com.romnan.githubfinduser.feature_user_detail.presentation.following_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.feature_user_detail.domain.model.FollowingUser


class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private val followingList = ArrayList<FollowingUser>()
    var onItemClick: ((FollowingUser) -> Unit)? = null

    fun setFollowingList(newFollowingList: List<FollowingUser>) {
        val diffResult = DiffUtil.calculateDiff(
            FollowingItemDiffCallback(oldItems = followingList, newItems = newFollowingList)
        )
        followingList.clear()
        followingList.addAll(newFollowingList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FollowingItemDiffCallback(
        val oldItems: List<FollowingUser>,
        val newItems: List<FollowingUser>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].username == newItems[newItemPosition].username

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(followingList[adapterPosition])
            }
        }

        fun bind(following: FollowingUser) {
            val ivProfilePicture = itemView.findViewById<ImageView>(R.id.iv_profile_picture)
            val tvUsername = itemView.findViewById<TextView>(R.id.tv_username)
            val tvType = itemView.findViewById<TextView>(R.id.tv_type)

            Glide.with(itemView)
                .load(following.avatarUrl)
                .circleCrop()
                .into(ivProfilePicture)
            tvUsername.text = following.username
            tvType.text = following.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder =
        FollowingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )


    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followingList[position])
    }

    override fun getItemCount(): Int = followingList.size
}