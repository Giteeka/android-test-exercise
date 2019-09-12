package com.app.proofofconcept.data.local

import androidx.room.*
import com.app.proofofconcept.data.model.RowItem

@Dao
interface RowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RowItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: List<RowItem>)

    @Query("SELECT * from RowItem")
    suspend fun getRowItems(): List<RowItem>

    @Query("DELETE from RowItem")
    suspend fun deleteAll()

}