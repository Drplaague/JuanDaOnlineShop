package com.example.juandaonlineshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.juandaonlineshop.R
import com.example.juandaonlineshop.app.ApiConfigAlamat
import com.example.juandaonlineshop.helper.Helper
import com.example.juandaonlineshop.model.Alamat
import com.example.juandaonlineshop.model.ModelAlamat
import com.example.juandaonlineshop.model.ResponOnkir
import com.example.juandaonlineshop.room.MyDatabase
import com.example.juandaonlineshop.util.ApiKey
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahAlamatActivity : AppCompatActivity() {

    var provinsi = ModelAlamat.Provinsi()
    var kota = ModelAlamat.Provinsi()
    var kecamatan = ModelAlamat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_alamat)
        Helper().setToolbar(this, toolbar, "Tambah Alamat")

        mainButton()
        getProvinsi()
    }

    private fun mainButton(){
        btn_simpan.setOnClickListener {
            simpan()
             
        }
    }
    private fun simpan() {
        when {
            edt_nama.text.isEmpty() -> {
                error(edt_nama)
                return
            }
            edt_type.text.isEmpty() -> {
                error(edt_type)
                return
            }
            edt_phone.text.isEmpty() -> {
                error(edt_phone)
                return
            }
            edt_alamat.text.isEmpty() -> {
                error(edt_alamat)
                return
            }
            edt_kodePos.text.isEmpty() -> {
                error(edt_kodePos)
                return
            }
        }
        if (provinsi.province_id == "0") {
            toast("Silahkan pilih provinsi")
            return
        }
        if (kota.city_id == "0") {
            toast("Silahkan pilih kota")
            return
        }
//        if (kecamatan.id == 0) {
//            toast("Silahkan pilih Kecamatan")
//            return
//        }

        val alamat = Alamat()
        alamat.name = edt_nama.text.toString()
        alamat.type = edt_type.text.toString()
        alamat.phone = edt_phone.text.toString()
        alamat.alamat = edt_alamat.text.toString()
        alamat.kodepos = edt_kodePos.text.toString()

        alamat.id_provinsi = Integer.valueOf(provinsi.province_id)
        alamat.provinsi = provinsi.province
        alamat.id_kota = Integer.valueOf(kota.city_id)
        alamat.kota = kota.city_name
//        alamat.id_kecamatan = kecamatan.id
//        alamat.kecamatan = kecamatan.nama

        insert(alamat)

    }

    private fun error(editText: EditText) {
        editText.error = "Kolom tidak boleh kosong"
        editText.requestFocus()
    }

    fun toast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun getProvinsi(){
        ApiConfigAlamat.instanceRetrofit.getProvinsi(ApiKey.key).enqueue(object : Callback<ResponOnkir> {
            override fun onFailure(call: Call<ResponOnkir>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponOnkir>, response: Response<ResponOnkir>) {
                if (response.isSuccessful){
                    pb.visibility = View.GONE
                    div_provinsi.visibility = View.VISIBLE

                    val res = response.body()!!
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Provinsi")
                    val listProvinsi = res.rajaongkir.results

                    for (prov in listProvinsi){
                        arryString.add(prov.province)
                    }
                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.item_spinner, arryString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_provinsi.adapter = adapter

                    spn_provinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if(position !=0){
                                provinsi = listProvinsi[position - 1]
                                val idProv = provinsi.province_id
                                getKota(idProv)
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                    }

                }else{
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }
        })
    }

    private fun getKota(id: String){
        pb.visibility = View.VISIBLE
        ApiConfigAlamat.instanceRetrofit.getKota(ApiKey.key, id).enqueue(object : Callback<ResponOnkir> {
            override fun onFailure(call: Call<ResponOnkir>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponOnkir>, response: Response<ResponOnkir>) {
                if (response.isSuccessful){
                    pb.visibility = View.GONE
                    div_kota.visibility = View.VISIBLE

                    val res = response.body()!!
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Kota")
                    val listKota = res.rajaongkir.results

                    for (kota in listKota) {
                        arryString.add(kota.type + " " + kota.city_name)
                    }
                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.item_spinner, arryString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kota.adapter = adapter

                    spn_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if(position !=0){
                                kota = listKota[position - 1]
                                val kodePos = kota.postal_code
                                edt_kodePos.setText(kodePos)
//                                getKecamatan(idKota)
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                    }

                }else{
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }
        })
    }

    private fun getKecamatan(id: Int){
        pb.visibility = View.VISIBLE
        ApiConfigAlamat.instanceRetrofit.getKecamatan(id).enqueue(object : Callback<ResponOnkir> {
            override fun onFailure(call: Call<ResponOnkir>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponOnkir>, response: Response<ResponOnkir>) {
                if (response.isSuccessful){
                    pb.visibility = View.GONE
                    div_kecamatan.visibility = View.VISIBLE

                    val res = response.body()!!
                    val arryString = ArrayList<String>()
                    arryString.add("Pilih Kecamatan")
                    val listKecamatan = res.kecamatan

                    for (prov in listKecamatan){
                        arryString.add(prov.nama)
                    }
                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.item_spinner, arryString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_kecamatan.adapter = adapter

                    spn_kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if(position !=0){
                                kecamatan = listKecamatan[position - 1]
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                    }


                }else{
                    Log.d("Error", "gagal memuat data:" + response.message())
                }
            }
        })
    }

    private fun insert(data: Alamat) {
        val myDb = MyDatabase.getInstance(this)!!
        if (myDb.daoAlamat().getByStatus(true) == null){
            data.isSelected = true
        }
        CompositeDisposable().add(Observable.fromCallable { myDb.daoAlamat().insert(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toast("Insert data success")
                onBackPressed()
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}