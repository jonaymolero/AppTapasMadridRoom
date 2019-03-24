package net.azarquiel.tapasroom.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Room
import android.content.Context

@Database(entities = [Establecimiento::class, Tapa::class], version = 1)
abstract class TapaDB: RoomDatabase() {
    abstract fun establecimientoDao(): EstablecimientoDao
    abstract fun tapaDao():TapaDao
    abstract fun tapaViewDao():TapaViewDao
    companion object {
        @Volatile
        private var INSTANCE: TapaDB? = null

        fun getDatabase(context: Context): TapaDB? {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TapaDB::class.java, "tapitas.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}