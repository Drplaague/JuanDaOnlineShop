package com.example.juandaonlineshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juandaonlineshop.R
import com.example.juandaonlineshop.adapter.AdapterBank
import com.example.juandaonlineshop.app.ApiConfig
import com.example.juandaonlineshop.model.Bank
import com.example.juandaonlineshop.model.Chekout
import com.example.juandaonlineshop.model.ResponModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_pembayaran.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        displayBank()
    }

    fun displayBank() {
        val arrBank = ArrayList<Bank>()
        arrBank.add(Bank("BCA", "092093871237", "Roipah", R.drawable.logo_bca))
        arrBank.add(Bank("BRI", "86721349128", "Roipah", R.drawable.logo_bri))
        arrBank.add(Bank("Mandiri", "02394870329", "Roipah", R.drawable.logo_madiri))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_data.layoutManager = layoutManager
        rv_data.adapter = AdapterBank(arrBank, object : AdapterBank.Listeners {
            override fun onClicked(data: Bank, index: Int) {
                bayar(data.nama)
            }
        })
    }

    fun bayar(bank : String) {
        val json = intent.getStringExtra("extra")!!.toString()
        val chekout = Gson().fromJson(json, Chekout::class.java)
        chekout.bank = bank

        ApiConfig.instanceRetrofit.chekout(chekout).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                Toast.makeText(this, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                val respon = response.body()!!
                if (respon.success == 1) {
                    Toast.makeText(this@PembayaranActivity, "Berhasil kirim ke server :" + respon.message, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@PembayaranActivity, "Error :" + respon.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}