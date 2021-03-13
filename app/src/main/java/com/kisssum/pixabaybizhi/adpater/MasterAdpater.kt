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

class MasterAdpater(private val context: Context) :
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
            val bundel = Bundle()
            bundel.putInt("type", 3)
            bundel.putInt("index", 0)
            bundel.putInt("position", position)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
        }
    }

    override fun getItemCount() = data.size
}