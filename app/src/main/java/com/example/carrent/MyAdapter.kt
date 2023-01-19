package com.example.carrent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val parkingList:ArrayList<com.example.carrent.List>,private val listener:OnItemClickListener):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=parkingList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.titletext.text=currentItem.heading
        holder.status.text= currentItem.status

    }

    override fun getItemCount(): Int {
        return  parkingList.size
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val status:TextView=itemView.findViewById(R.id.item_status)
        val titleImage: ImageView=itemView.findViewById(R.id.item_image)
        val titletext: TextView=itemView.findViewById(R.id.item_text)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position:Int=adapterPosition
            if (position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }



}