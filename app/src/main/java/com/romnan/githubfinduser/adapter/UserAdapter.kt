package com.romnan.githubfinduser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romnan.githubfinduser.R
import com.romnan.githubfinduser.model.User

class UserAdapter(private val usersList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var onItemClick: ((User) -> Unit)? = null

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rootView.setOnClickListener {
                onItemClick?.invoke(usersList[adapterPosition])
            }
        }

        fun bind(user: User) {
            val ivProfilePicture = itemView.findViewById<ImageView>(R.id.iv_profile_picture)
            val tvName = itemView.findViewById<TextView>(R.id.tv_name)
            val tvUsername = itemView.findViewById<TextView>(R.id.tv_username)
            val tvLocation = itemView.findViewById<TextView>(R.id.tv_location)

            Glide.with(itemView)
                .load(user.avatar)
                .circleCrop()
                .into(ivProfilePicture)
            tvName.text = user.name
            tvUsername.text = user.username
            tvLocation.text = user.location
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