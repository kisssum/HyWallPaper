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
import com.kisssum.pixabaybizhi.databinding.ModelListItem2Binding

class TypesListAdpater(private val context: Context, private val typeIndex: Int) :
    RecyclerView.Adapter<TypesListAdpater.MyViewHolder>() {
    private var data = arrayListOf<Map<String, String>>()

    class MyViewHolder(binding: ModelListItem2Binding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.img
    }

    fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ModelListItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = data[position]

        Glide.with(context)
            .load(url["lazysrc"])
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            val bundel = Bundle()
            bundel.putInt("type", 3)
            bundel.putInt("index", typeIndex)
            bundel.putInt("position", position)

            Navigation.findNavController(context as Activity, R.id.fragment_main)
                .navigate(R.id.action_navigationControlFragment_to_imgMainFragment, bundel)
        }
    }

    override fun getItemCount() = data.size
}