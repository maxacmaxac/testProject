package com.example.marjancvetkovic.corutinesexample.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.marjancvetkovic.corutinesexample.model.Bmf

@Dao
interface BmfDao {
    @get:Query("SELECT * FROM Bmf ORDER BY Bmf.zip")
    val allOffices: List<Bmf>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(offices: List<Bmf>)

    @Query("DELETE FROM Bmf")
    fun deleteAll()
}