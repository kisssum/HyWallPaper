package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
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
import kotlin.collections.ArrayList

class TypesAdpater(
    private val context: Context,
    private val typeIndex: Int,
    private val isNavPager: Boolean,
    private val isLargeImg: Boolean = false,
) :
    RecyclerView.Adapter<TypesAdpater.MyViewHolder>() {
    private var data = arrayListOf<Map<String, String>>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = if (isLargeImg) {
            LayoutInflater.from(parent.context).inflate(R.layout.model_list_item_2, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.model_list_item_3, parent, false)
        }
        return MyViewHolder(item)
    }

    fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data[position]

        Glide.with(context)
            .load(obj["lazysrc"])
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 3)
            bundel.putInt("index", typeIndex)
            bundel.putInt("position", position)

            if (isNavPager) {
                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
            } else {
                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_typesPagerFragment_to_imgMainFragment, bundel)
            }
        }
    }

    override fun getItemCount() = data.size
}