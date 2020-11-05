package com.kisssum.pixabaybizhi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RvAdpater(var context: Context) : RecyclerView.Adapter<RvAdpater.MyViewHolder>() {
    private var data: List<Map<String, String>> = ArrayList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.r_img)
        var user = itemView.findViewById<TextView>(R.id.r_user)
        var likes = itemView.findViewById<TextView>(R.id.r_likes)
        var favorites = itemView.findViewById<TextView>(R.id.r_favorites)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(item)
    }

    fun setData(datas: List<Map<String, String>>) {
        this.data = datas
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]
        val imgUrl = map["webformatURL"]

        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.let {
            it.img.layoutParams.height = map["webformatHeight"]!!.toInt()
            it.user.text = map["user"]
            it.likes.text = map["likes"]
            it.favorites.text = map["favorites"]

            it.itemView.setOnClickListener {
                val bundel = Bundle()
                bundel.putString("webformatURL", map["webformatURL"])
                bundel.putString("largeImageURL", map["largeImageURL"])

                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_detailFragment, bundel)
            }
        }
    }

    override fun getItemCount() = data.size
}

