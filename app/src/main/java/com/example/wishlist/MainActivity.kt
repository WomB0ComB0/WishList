package com.example.wishlist

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.typeOf

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

        fun clicableLink(longText: String) {
            try {
                print("Clicked function")
                val spanned = SpannableString(longText)
                val matcher = Patterns.WEB_URL.matcher(longText)
                var matchStart: Int
                var matchEnd: Int

                while (matcher.find()) {
                    matchStart = matcher.start()
                    matchEnd = matcher.end()

                    var url = longText.substring(matchStart, matchEnd)
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://$url"
                    }

                    val clickableSpan: ClickableSpan = object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.color = Color.BLUE
                            ds.isUnderlineText = true
                        }
                    }
                    spanned.setSpan(clickableSpan, matchStart, matchEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                itemURL.text = spanned.toString() as Editable
                println(itemURL.text.javaClass)
                itemURL.movementMethod = LinkMovementMethod.getInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


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
        clicableLink(itemURL.text.toString())
    }
}