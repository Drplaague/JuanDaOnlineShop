package com.example.juandaonlineshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.juandaonlineshop.R
import com.example.juandaonlineshop.adapter.AdapterProduk
import com.example.juandaonlineshop.adapter.AdapterSlider
import com.example.juandaonlineshop.app.ApiConfig
import com.example.juandaonlineshop.model.Produk
import com.example.juandaonlineshop.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvProdukTerlasir: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()

        return view
    }

    fun init(view: View) {

        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)

    }
    fun displayProduk(){


        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL


        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager

        rvProdukTerlasir.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProdukTerlasir.layoutManager = layoutManager2



    }
    private var listProduk: ArrayList<Produk> = ArrayList()
    fun getProduk() {
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    val arrayProduk = ArrayList<Produk>()
                    for (p in res.produks) {
                        p.discount = 5000
                        arrayProduk.add(p)
                    }

                    listProduk = arrayProduk
                    displayProduk()
                    }
                }
        })
    }

}