package com.example.juandaonlineshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juandaonlineshop.R
import com.example.juandaonlineshop.adapter.AdapterKeranjang
import com.example.juandaonlineshop.helper.Helper
import com.example.juandaonlineshop.model.Produk
import com.example.juandaonlineshop.room.MyDatabase

class KeranjangFragment : Fragment() {

    lateinit var mydb : MyDatabase
    //di panggil sekali ketika aktif
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        mydb = MyDatabase.getInstance(requireActivity())!!

        mainButton()

        return view
    }

    lateinit var adapter : AdapterKeranjang
    var listProduk = ArrayList<Produk>()

    private fun displayproduk(){
        listProduk = mydb.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter = AdapterKeranjang(requireActivity(), listProduk, object : AdapterKeranjang.Listeners{
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

        })
        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }

    fun hitungTotal(){
        val listProduk = mydb.daoKeranjang().getAll() as ArrayList

        var totalHarga = 0
        for (produk in listProduk){

            val harga = Integer.valueOf(produk.harga)
            totalHarga += (harga * produk.jumlah)
        }
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton(){
        btnDelete.setOnClickListener {

        }

        btnBayar.setOnClickListener {

        }


    }

    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView
    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)

    }

    override fun onResume() {
        displayproduk()
        hitungTotal()
        super.onResume()
    }

}