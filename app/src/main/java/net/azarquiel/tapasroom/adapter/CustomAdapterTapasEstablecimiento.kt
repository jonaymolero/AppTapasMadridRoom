package net.azarquiel.tapasroom.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rowtapaestablecimiento.view.*
import net.azarquiel.tapasroom.model.Tapa

/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapterTapasEstablecimiento(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapterTapasEstablecimiento.ViewHolder>() {

    private var dataList: List<Tapa> = emptyList()

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

    internal fun setTapas(tapas: List<Tapa>) {
        this.dataList = tapas
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Tapa){
            itemView.tvTapaEstablecimiento.text=dataItem.nombre
        }

    }
}