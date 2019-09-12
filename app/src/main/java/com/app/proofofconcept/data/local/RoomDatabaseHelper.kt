package com.app.proofofconcept.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.proofofconcept.data.model.RowItem


/**
 * Room database for local storage
 *
 * Table entities = RowItem.class
 * database name = Result-database
 */
@Database(entities = [RowItem::class], version = 1)
abstract class RoomDatabaseHelper : RoomDatabase() {
    abstract fun rowDao(): RowDao

    companion object {
        fun getInstance(context: Context): RoomDatabaseHelper {
            return Room.databaseBuilder(context, RoomDatabaseHelper::class.java, "Result-database")
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}