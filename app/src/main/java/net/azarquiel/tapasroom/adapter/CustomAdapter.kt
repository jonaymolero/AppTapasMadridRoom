package net.azarquiel.tapasroom.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rowtapas.view.*
import net.azarquiel.tapasroom.model.TapaView

class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<TapaView> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setTapas(tapas: List<TapaView>) {
        this.dataList = tapas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: TapaView){
            itemView.tvNombreTapaRow.text=dataItem.nombreTapa
            itemView.tvNombreBarRow.text=dataItem.nombreEstablecimiento
            itemView.tvDescripcionRow.text=dataItem.descripcion
            Picasso.with(context).load("http://82.223.108.85/storage/${dataItem.url_imagen}").into(itemView.ivTapaRow)
            itemView.tag = dataItem
        }

    }
}