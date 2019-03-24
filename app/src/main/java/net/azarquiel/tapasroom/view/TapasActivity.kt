package net.azarquiel.tapasroom.view

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_tapas.*
import kotlinx.android.synthetic.main.content_tapas.*
import net.azarquiel.tapasroom.R
import net.azarquiel.tapasroom.adapter.CustomAdapter
import net.azarquiel.tapasroom.model.TapaView
import net.azarquiel.tapasroom.util.Util
import net.azarquiel.tapasroom.viewmodel.TapaViewViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class TapasActivity : AppCompatActivity(), SearchView.OnQueryTextListener  {

    companion object {
        private const val REQUEST_ADD=0
    }

    private lateinit var tapaViewVieModel:TapaViewViewModel
    private lateinit var listaTapas:List<TapaView>
    private lateinit var adapter:CustomAdapter
    private lateinit var favoritos:SharedPreferences
    private var isFavorito:Boolean=false
    private lateinit var listaFavoritos:ArrayList<TapaView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tapas)
        setSupportActionBar(toolbar)
        Util.inyecta(this)
        favoritos = getSharedPreferences("favoritos", Context.MODE_PRIVATE)
        tapaViewVieModel = ViewModelProviders.of(this).get(TapaViewViewModel::class.java)
        listaFavoritos= ArrayList()
        datosSharePreferences()
        crearAdapter()
        doAsync {
            listaTapas=tapaViewVieModel.getTapas()
            uiThread {
                adapter.setTapas(listaTapas)
            }
        }
    }

    private fun datosSharePreferences() {
        val tapasShare = favoritos.all
        for (entry in tapasShare.entries) {
            val jsonTapa=entry.value.toString()
            val pokemonLista: TapaView= Gson().fromJson(jsonTapa, TapaView::class.java)
            listaFavoritos.add(pokemonLista)
        }
    }

    private fun crearAdapter() {
        adapter= CustomAdapter(this,R.layout.rowtapas)
        rvTapas.layoutManager=LinearLayoutManager(this)
        rvTapas.adapter=adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tapas, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.search -> true
            R.id.favoritos -> sacarFavoritos(item)
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun pulsarTapa(v:View){
        var tapaPulsada=v.tag as TapaView
        var intent= Intent(this, DetailTapaActivity::class.java)
        intent.putExtra("tapaPulsada", tapaPulsada)
        startActivityForResult(intent, REQUEST_ADD)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(isFavorito){
            adapter.setTapas(listaFavoritos.filter { p -> p.nombreTapa.toLowerCase().contains(query!!.toLowerCase()) })
        }else{
            adapter.setTapas(listaTapas.filter { p -> p.nombreTapa.toLowerCase().contains(query!!.toLowerCase()) })
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val tapaDevuelta = data!!.getSerializableExtra("tapaDevuelta") as TapaView
            val isAdd=data!!.getBooleanExtra("isFavorito",false)
            val editor = favoritos.edit()
            if(!favoritos.contains(tapaDevuelta.idTapa.toString())){
                if(isAdd){
                    val jsonTapa: String = Gson().toJson(tapaDevuelta)
                    editor.putString(tapaDevuelta.idTapa.toString(), jsonTapa)
                    listaFavoritos.add(tapaDevuelta)
                }
            }
            if(!isAdd){
                editor.remove(tapaDevuelta.idTapa.toString())
                listaFavoritos.remove(tapaDevuelta)
            }
            editor.commit()
            if(isFavorito){
                adapter.setTapas(listaFavoritos)
            }
        }
    }

    private fun sacarFavoritos(item: MenuItem): Boolean {
        isFavorito=!isFavorito
        if(isFavorito){
            title="Tapas favoritas"
            item.setIcon(android.R.drawable.btn_star_big_on)
            adapter.setTapas(listaFavoritos)
        }else{
            title="Todas las tapas"
            item.setIcon(android.R.drawable.btn_star_big_off)
            adapter.setTapas(listaTapas)
        }
        return true
    }
}
