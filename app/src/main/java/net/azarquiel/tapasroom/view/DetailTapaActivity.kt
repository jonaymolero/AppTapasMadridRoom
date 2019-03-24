package net.azarquiel.tapasroom.view

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Icon
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_detail_tapa.*
import kotlinx.android.synthetic.main.content_detail_tapa.*
import kotlinx.android.synthetic.main.rowtapas.view.*
import net.azarquiel.tapasroom.R
import net.azarquiel.tapasroom.model.Establecimiento
import net.azarquiel.tapasroom.model.TapaView
import net.azarquiel.tapasroom.viewmodel.EstablecimientoViewModel
import net.azarquiel.tapasroom.viewmodel.TapaViewViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class DetailTapaActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_ADD=0
    }

    private lateinit var tapaPulsada:TapaView
    private lateinit var establecimientoViewModel:EstablecimientoViewModel
    private lateinit var establecimientoDeLaTapa:Establecimiento
    private var isFavorito:Boolean=false
    private lateinit var favoritos:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tapa)
        setSupportActionBar(toolbar)
        tapaPulsada=intent.getSerializableExtra("tapaPulsada") as TapaView
        favoritos = getSharedPreferences("favoritos", Context.MODE_PRIVATE)
        establecimientoViewModel = ViewModelProviders.of(this).get(EstablecimientoViewModel::class.java)
        title=tapaPulsada.nombreTapa
        doAsync {
            establecimientoDeLaTapa=establecimientoViewModel.getEstablecimiento(tapaPulsada.idEstablecimiento)
            uiThread {
                pintar()
            }
        }
        comprobarFavorito()
        fab.setOnClickListener {cambiarFavoritos()}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val isAdd=data!!.getBooleanExtra("isFavorito",false)
            isFavorito=isAdd
        }
    }

    private fun comprobarFavorito() {
        val jsonPokemon = favoritos.getString(tapaPulsada.idTapa.toString(),"nosta")
        if(!jsonPokemon.equals("nosta")){
            fab.setImageResource(android.R.drawable.btn_star_big_on)
            isFavorito=true
        }
    }

    private fun cambiarFavoritos() {
        isFavorito=!isFavorito
        if(isFavorito){
            fab.setImageResource(android.R.drawable.btn_star_big_on)
        }else{
            fab.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun pintar() {
        Picasso.with(this).load("http://82.223.108.85/storage/${tapaPulsada.url_imagen}").into(ivTapaDetail)
        tvNombreTapaDetail.text=tapaPulsada.nombreTapa
        tvDescripciontapaDetail.text=tapaPulsada.descripcion
        tvNombreEstablecimientoDetail.text=establecimientoDeLaTapa.nombre
        tvDireccionEstablecimiento.text=establecimientoDeLaTapa.direccion
        Picasso.with(this).load("http://82.223.108.85/storage/${establecimientoDeLaTapa.url_imagen_exterior}").into(ivEstablecimientoDetail)
    }

    fun pulsarEstablecimiento(v:View){
        var intent= Intent(this, EstablecimientoDetailActivity::class.java)
        intent.putExtra("establecimiento", establecimientoDeLaTapa)
        intent.putExtra("isFavortito",isFavorito)
        startActivityForResult(intent, DetailTapaActivity.REQUEST_ADD)
    }

    override fun onBackPressed() {
        val intent = Intent(this, TapasActivity::class.java)
        intent.putExtra("tapaDevuelta", tapaPulsada)
        intent.putExtra("isFavorito", isFavorito)
        setResult(Activity.RESULT_OK,intent)
        super.onBackPressed()
    }
}
