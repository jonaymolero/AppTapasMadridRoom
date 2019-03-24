package net.azarquiel.tapasroom.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "establecimiento")
data class Establecimiento(@PrimaryKey
                   var id: Int,
                   var nombre:String,
                   var direccion:String,
                   var telefono:String,
                   var url_imagen_exterior:String,
                   var latitud:Float,
                   var longitud:Float):Serializable

@Entity(tableName = "tapa",
        foreignKeys = [ForeignKey(entity = Establecimiento::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("id_establecimiento"))])
data class Tapa(@PrimaryKey
                           var id: Int,
                           var id_establecimiento:Int,
                           var nombre:String,
                            var descripcion:String,
                            var url_imagen:String):Serializable

data class TapaView(var idTapa:Int,var nombreTapa:String,var descripcion:String,var url_imagen:String,var idEstablecimiento:Int,var nombreEstablecimiento:String):Serializable
