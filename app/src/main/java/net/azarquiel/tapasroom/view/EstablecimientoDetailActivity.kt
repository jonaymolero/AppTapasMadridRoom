package net.azarquiel.tapasroom.view

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_establecimiento_detail.*
import kotlinx.android.synthetic.main.content_detail_tapa.*
import kotlinx.android.synthetic.main.content_establecimiento_detail.*
import net.azarquiel.tapasroom.R
import net.azarquiel.tapasroom.adapter.CustomAdapterTapasEstablecimiento
import net.azarquiel.tapasroom.model.Establecimiento
import net.azarquiel.tapasroom.model.Tapa
import net.azarquiel.tapasroom.viewmodel.EstablecimientoViewModel
import net.azarquiel.tapasroom.viewmodel.TapaViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EstablecimientoDetailActivity : AppCompatActivity() {

    private lateinit var establecimientoPulsado:Establecimiento
    private lateinit var tapaViewModel:TapaViewModel
    private lateinit var listaTapas:List<Tapa>
    private lateinit var adapter:CustomAdapterTapasEstablecimiento
    private var isFavorito:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_establecimiento_detail)
        setSupportActionBar(toolbar)
        establecimientoPulsado=intent.getSerializableExtra("establecimiento") as Establecimiento
        isFavorito=intent.getBooleanExtra("isFavortito",false)
        title=establecimientoPulsado.nombre
        tapaViewModel = ViewModelProviders.of(this).get(TapaViewModel::class.java)
        createAdapter()
        doAsync {
            listaTapas=tapaViewModel.getTapaDeUnEstablecimiento(establecimientoPulsado.id)
            uiThread {
                pintar()
            }
        }
    }

    private fun createAdapter() {
        adapter=CustomAdapterTapasEstablecimiento(this,R.layout.rowtapaestablecimiento)
        rvTapasDetail.layoutManager=LinearLayoutManager(this)
        rvTapasDetail.adapter=adapter
    }

    private fun pintar() {
        adapter.setTapas(listaTapas)
        Picasso.with(this).load("http://82.223.108.85/storage/${establecimientoPulsado.url_imagen_exterior}").into(ivEstablecimientoDetailEst)
        tvNombreEstablecimientoEst.text=establecimientoPulsado.nombre
        tvDireccionEstablecimientoEst.text=establecimientoPulsado.direccion
    }

    override fun onBackPressed() {
        val intent = Intent(this, DetailTapaActivity::class.java)
        intent.putExtra("isFavorito", isFavorito)
        setResult(Activity.RESULT_OK,intent)
        super.onBackPressed()
    }
}
