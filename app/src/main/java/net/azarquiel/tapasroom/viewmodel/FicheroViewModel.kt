package net.azarquiel.tapasroom.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import net.azarquiel.tapasroom.model.*

class EstablecimientoViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: EstablecimientoRepository =
            EstablecimientoRepository(application)

    fun getEstablecimiento(idEstablecimiento:Int):Establecimiento{
        return repository.getEstablecimientos(idEstablecimiento)
    }
}

class TapaViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: TapaRepository =
            TapaRepository(application)

    fun getTapaDeUnEstablecimiento(idEstablecimiento:Int):List<Tapa>{
        return repository.getTapasDeUnEstablecimiento(idEstablecimiento)
    }
}
class TapaViewViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: TapaViewRepository =
            TapaViewRepository(application)

    fun getTapas():List<TapaView>{
        return repository.getTapasView()
    }
}