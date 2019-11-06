package com.gamehub.iso

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.item_countries.view.*
import kotlinx.android.synthetic.main.item_select_country.view.*

class FlagAdapter(private val context: Context, private val countries: List<Country>) : BaseAdapter() {
    override fun getItem(position: Int): Country {
        return countries[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return countries.size
    }

    // 스피너가 닫혀 있는 상태
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: SpinnerViewHolder
        val retView: View

        if(convertView == null){
            retView = LayoutInflater.from(context).inflate(R.layout.item_select_country, null)
            holder = SpinnerViewHolder(retView)
            retView.tag = holder

        } else {
            holder = convertView.tag as SpinnerViewHolder
            retView = convertView
        }

        holder.bindView(getItem(position))

        return retView
    }

    // 스피너가 열릴 때 상태
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: DropdownViewHolder
        val retView: View

        if(convertView == null){
            retView = LayoutInflater.from(context).inflate(R.layout.item_countries, null)
            holder = DropdownViewHolder(retView)
            retView.tag = holder

        } else {
            holder = convertView.tag as DropdownViewHolder
            retView = convertView
        }

        holder.bindView(getItem(position))

        return retView
    }

    class DropdownViewHolder (view: View) {
        // Holds the TextView that will add each animal to
        private val tvFlagCountry: TextView = view.tvCountry

        fun bindView(country: Country) {
            tvFlagCountry.text = "${PhoneManager.getEmojiFlag(country.iso2)}  ${PhoneManager.getLocaleCountryName(country.iso2)} +${country.dialCode}"
        }
    }

    class SpinnerViewHolder (view: View) {
        // Holds the TextView that will add each animal to
        private val tvSelectCountry: TextView = view.tvSelectCountry

        fun bindView(country: Country) {
            tvSelectCountry.text = "${PhoneManager.getEmojiFlag(country.iso2)}  +${country.dialCode}"
        }
    }
}