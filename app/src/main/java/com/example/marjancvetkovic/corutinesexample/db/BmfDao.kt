package com.example.marjancvetkovic.corutinesexample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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