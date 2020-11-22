package com.kisssum.pixabaybizhi.Bian

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.R

class BianPagerAdpater(var context: Context) :
    RecyclerView.Adapter<BianPagerAdpater.MyViewHolder>() {
    private var data = arrayListOf<String>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.img)
    }

    fun setData(data: ArrayList<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<String>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bian_pager_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = data[position]

        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 2)
            bundel.putString("imgUrl", url)

            Navigation.findNavController(it)
                .navigate(R.id.action_bianFragment_to_detailFragment, bundel)
        }
    }

    override fun getItemCount() = data.size
}