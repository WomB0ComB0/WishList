package com.example.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishListAdapter(private val items: List<WishList>) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(itemsView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemsView) {

        val itemTextView: TextView
        val priceTextView: TextView
        val urlTextView: TextView

        init {
            itemTextView = itemsView.findViewById(R.id.item_Name)
            priceTextView = itemsView.findViewById(R.id.item_Price)
            urlTextView = itemsView.findViewById(R.id.item_Url)

            itemsView.setOnLongClickListener {
                listener.onItemClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val listView = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(listView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        println("Item: ${item.ItemName}, Price: ${item.ItemPrice}, URL: ${item.ItemLink}")
        holder.itemTextView.text = item.ItemName
        holder.priceTextView.text = item.ItemPrice
        holder.urlTextView.text = item.ItemLink
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
