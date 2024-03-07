package com.example.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var items: MutableList<WishList> = mutableListOf()
        val button  = findViewById<Button>(R.id.submitButton)
        val itemName = findViewById<TextView>(R.id.ItemName) as EditText
        val itemPrice = findViewById<TextView>(R.id.ItemPrice) as EditText
        val itemURL = findViewById<TextView>(R.id.ItemUrl) as EditText
        val Rv = findViewById<RecyclerView>(R.id.Rv)

        itemName.hint = "Item name"
        itemPrice.hint = "$0.00"
        itemURL.hint = "https://example.com"

        val adapter = WishListAdapter(items)
        Rv.adapter = adapter
        Rv.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {
            val name = itemName.text.toString()
            val price = itemPrice.text.toString()
            val url = itemURL.text.toString()
            val newItem = WishList(name, price, url)

            items.add(newItem)
            adapter.notifyDataSetChanged()

            itemName.text.clear()
            itemPrice.text.clear()
            itemURL.text.clear()
        }

        adapter.setOnItemClickListener(object: WishListAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "Item removed at position ${position + 1}", Toast.LENGTH_LONG).show()
                items.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })
    }
}