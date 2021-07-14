package com.example.juandaonlineshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juandaonlineshop.R
import com.example.juandaonlineshop.adapter.AdapterProdukTransaksi
import com.example.juandaonlineshop.helper.Helper
import com.example.juandaonlineshop.model.DetailTransaksi
import com.example.juandaonlineshop.model.Transaksi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailTransaksiActivity : AppCompatActivity() {

    var transaksi = Transaksi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, Transaksi::class.java)

        setData(transaksi)
        displayProduk(transaksi.details)
    }

    fun setData(t: Transaksi) {
        tv_status.text = t.status
        tv_tgl.text = t.created_at
        tv_penerima.text = t.name + " - " + t.phone
        tv_alamat.text = t.detail_lokasi
        tv_kodeUnik.text = Helper().gantiRupiah(t.kode_unik)
        tv_totalBelanja.text = Helper().gantiRupiah(t.total_harga)
        tv_ongkir.text = Helper().gantiRupiah(t.ongkir)
        tv_total.text = Helper().gantiRupiah(t.total_transfer)

        if (t.status != "MENUNGGU") div_footer.visibility = View.GONE

        var color = getColor(R.color.menungu)
        if (t.status == "SELESAI") color = getColor(R.color.selesai)


        tv_status.setTextColor(color)
    }

    fun displayProduk(transaksis: ArrayList<DetailTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterProdukTransaksi(transaksis)
        rv_produk.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}