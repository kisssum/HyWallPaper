package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.ModelListItem3Binding
import kotlin.collections.ArrayList

class MasterAdpater(private val context: Context, private val typeIndex: Int) :
    RecyclerView.Adapter<MasterAdpater.MyViewHolder>() {
    private var data = arrayListOf<Map<String, String>>()

    class MyViewHolder(binding: ModelListItem3Binding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.img
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ModelListItem3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
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
            if (typeIndex == 0) {
                val bundel = Bundle()
                bundel.putInt("type", 9)
                bundel.putInt("index", position)
               
                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
            } else {
                val bundel = Bundle()
                bundel.putInt("type", 3)
                bundel.putString("href", obj["href"])
                bundel.putString("lazysrc2x", obj["lazysrc2x"])

                Navigation.findNavController(context as Activity, R.id.fragment_main)
                    .navigate(R.id.action_typesPagerFragment_to_imgMainFragment, bundel)
            }
        }
    }

    override fun getItemCount() = data.size
}