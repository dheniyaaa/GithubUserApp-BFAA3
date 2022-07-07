package com.example.submission3.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3.data.source.local.entity.UserModel
import com.example.submission3.databinding.ItemFollowBinding

class FollowAdapter(private val listener: (UserModel) -> Unit): RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    private var listUserData = ArrayList<UserModel>()

    fun setDataUser(userData: List<UserModel>){

        listUserData.clear()
        listUserData.addAll(userData)

        //this.listUserData.clear()
        //this.listUserData.addAll(userData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(listUserData[position])

    override fun getItemCount(): Int {
        return listUserData.size
    }


    inner class ListViewHolder(private val binding: ItemFollowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserModel){
            with(binding){
                nameUser.text = userData.username
                Glide.with(itemView.context)
                    .load(userData.image)
                    .into(imgUser)
                companyUser.text = userData.company

                itemView.setOnClickListener{
                    listener(userData)
                }
            }
        }

    }

}