package com.romnan.githubfinduser.feature_search_user.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.feature_search_user.domain.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val usersList = ArrayList<User>()
    var onItemClick: ((User) -> Unit)? = null

    fun setUsersList(newUsersList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(
            UserItemDiffCallback(oldItems = usersList, newItems = newUsersList)
        )
        usersList.clear()
        usersList.addAll(newUsersList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserItemDiffCallback(
        val oldItems: List<User>,
        val newItems: List<User>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].username == newItems[newItemPosition].username

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(usersList[adapterPosition])
            }
        }

        fun bind(user: User) {
            val ivProfilePicture = itemView.findViewById<ImageView>(R.id.iv_profile_picture)
            val tvUsername = itemView.findViewById<TextView>(R.id.tv_username)
            val tvType = itemView.findViewById<TextView>(R.id.tv_type)

            Glide.with(itemView)
                .load(user.avatarUrl)
                .circleCrop()
                .into(ivProfilePicture)
            tvUsername.text = user.username
            tvType.text = user.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size
}