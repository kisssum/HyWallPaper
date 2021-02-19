package com.kisssum.pixabaybizhi.NavHome.Pixabay

import android.app.Activity
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
import com.kisssum.pixabaybizhi.R

class PixabayListAdpater(private val context: Context) :
    RecyclerView.Adapter<PixabayListAdpater.MyViewHolder>() {
    private var data: List<Map<String, String>> = ArrayList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.img)
        var user = itemView.findViewById<TextView>(R.id.r_user)
        var likes = itemView.findViewById<TextView>(R.id.r_likes)
        var favorites = itemView.findViewById<TextView>(R.id.r_favorites)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.pixabay_list_item, parent, false)
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
                bundel.putInt("type", 1)
                bundel.putInt("indexImg", position)

                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
            }
        }
    }

    override fun getItemCount() = data.size
}

