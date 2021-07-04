package com.example.juandaonlineshop.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.juandaonlineshop.model.Alamat
import com.example.juandaonlineshop.model.Produk


@Dao
interface DaoAlamat {

    @Insert(onConflict = REPLACE)
    fun insert(data: Alamat)

    @Delete
    fun delete(data: Alamat)

    @Delete
    fun delete(data: List<Alamat>)

    @Update
    fun update(data: Alamat): Int

    @Query("SELECT * from alamat ORDER BY id ASC")
    fun getAll(): List<Alamat>

    @Query("SELECT * FROM alamat WHERE id = :id LIMIT 1")
    fun getProduk(id: Int): Alamat

    @Query("DELETE FROM alamat WHERE id = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM alamat")
    fun deleteAll(): Int
}