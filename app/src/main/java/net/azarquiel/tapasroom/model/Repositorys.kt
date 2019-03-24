package net.azarquiel.tapasroom.model

import android.app.Application

class EstablecimientoRepository(application: Application) {

    val establecimientoDao = TapaDB.getDatabase(application)!!.establecimientoDao()
    // select
    fun getEstablecimientos(idEstablecimiento:Int):Establecimiento{
        return establecimientoDao.getEstablecimiento(idEstablecimiento)
    }
}

class TapaRepository(application: Application){
    val tapaDao=TapaDB.getDatabase(application)!!.tapaDao()

    fun getTapasDeUnEstablecimiento(idEstablecimiento:Int):List<Tapa>{
        return tapaDao.getTapasDeUnEstablecimiento(idEstablecimiento)
    }
}

class TapaViewRepository(application: Application){
    val tapaViewDao=TapaDB.getDatabase(application)!!.tapaViewDao()

    fun getTapasView():List<TapaView>{
        return tapaViewDao.getTapaView()
    }
}