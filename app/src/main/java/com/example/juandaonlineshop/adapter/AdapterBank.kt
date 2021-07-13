package com.example.juandaonlineshop.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.juandaonlineshop.R
import com.example.juandaonlineshop.activity.DetailProdukActivity
import com.example.juandaonlineshop.activity.LoginActivity
import com.example.juandaonlineshop.helper.Helper
import com.example.juandaonlineshop.model.Alamat
import com.example.juandaonlineshop.model.Bank
import com.example.juandaonlineshop.model.Produk
import com.example.juandaonlineshop.model.rajaongkir.Costs
import com.example.juandaonlineshop.model.rajaongkir.Result
import com.example.juandaonlineshop.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterBank(var data: ArrayList<Bank>, var listener: Listeners): RecyclerView.Adapter<AdapterBank.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
        val image = view.findViewById<ImageView>(R.id.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]
        holder.tvNama.text = a.nama
        holder.image.setImageResource(a.image)
        holder.layout.setOnClickListener {
            listener.onClicked(a, holder.adapterPosition)
        }
    }

    interface Listeners {
        fun onClicked(data: Bank, index: Int)
    }

}