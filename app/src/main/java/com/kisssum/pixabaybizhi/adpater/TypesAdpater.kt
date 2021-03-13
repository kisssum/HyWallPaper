package com.kisssum.pixabaybizhi.adpater

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.pixabaybizhi.R
import com.kisssum.pixabaybizhi.databinding.ModelListItemBinding
import com.kisssum.pixabaybizhi.state.TypesViewModel

class TypesAdpater(private val context: Context, private val viewModel: TypesViewModel) :
    RecyclerView.Adapter<TypesAdpater.MyViewHolder>() {

    private var data = arrayListOf<Map<String, String>>()

    class MyViewHolder(binding: ModelListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val type = binding.type
        val count = binding.count
        val enter = binding.enter
        val list = binding.list
        val topTitle = binding.topTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ModelListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data[position]
        val activity = context as Activity

        holder.type.text = obj["name"]
        holder.count.text = obj["count"]
        holder.topTitle.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", position)

            Navigation.findNavController(activity, R.id.fragment_main)
                .navigate(R.id.action_navigationControlFragment_to_typesPagerFragment, bundle)
        }

        // list
        val adapters = TypesListAdpater(context, position)
        viewModel.getPictureData(position).observe(context as LifecycleOwner) {
            adapters.setData(it)
        }
        viewModel.loadPictureData(position)

        holder.list.apply {
            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = adapters
        }
    }

    fun setData(data: ArrayList<Map<String, String>>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size
}