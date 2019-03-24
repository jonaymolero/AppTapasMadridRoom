package net.azarquiel.tapasroom.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface EstablecimientoDao {
    @Query("SELECT * from establecimiento where id=:idEstablecimiento")
    fun getEstablecimiento(idEstablecimiento: Int): Establecimiento
}

@Dao
interface TapaDao {
    @Query("select * from tapa where id_establecimiento=:idEstablecimiento")
    fun getTapasDeUnEstablecimiento(idEstablecimiento:Int): List<Tapa>
}

@Dao
interface TapaViewDao {
    @Query("select t.id idTapa,t.nombre nombreTapa,t.descripcion,t.url_imagen,es.id idEstablecimiento,es.nombre nombreEstablecimiento from tapa t, establecimiento es where es.id=t.id_establecimiento")
    fun getTapaView(): List<TapaView>
}