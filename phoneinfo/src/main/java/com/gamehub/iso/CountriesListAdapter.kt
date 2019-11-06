package com.gamehub.iso

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.emoji.text.EmojiCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_countries.view.*
import java.util.*

class CountriesListAdapter(private val context: Context, private val values: Array<String>) : RecyclerView.Adapter<CountriesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_countries, parent, false))
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val g = values[position].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val countryName =  getCountryZipCode(g[1]).trim { it <= ' ' }

        val flagEmoji = PhoneManager.getEmojiFlag(g[1])
        val processed = EmojiCompat.get().process("$countryName $flagEmoji")
        holder.tvCountry.text = processed
    }

    private fun getCountryZipCode(ssid: String): String {
        val loc = Locale("", ssid)
        return loc.displayCountry.trim()
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvCountry: TextView = view.tvCountry
    }

}