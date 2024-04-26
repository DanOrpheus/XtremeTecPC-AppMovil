package equipo6.xtremetecpc.xtpc_app.adaptadores

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import equipo6.xtremetecpc.xtpc_app.Buscador1
import equipo6.xtremetecpc.xtpc_app.Producto
import equipo6.xtremetecpc.xtpc_app.R

import java.util.Locale
import java.util.stream.Collectors


class adaptadorProductoBusqueda (private var productosBusqueda: List<Producto>) : RecyclerView.Adapter<adaptadorProductoBusqueda.ProductoBusquedaViewHolder>() {

    private val listaOriginal: ArrayList<Producto> = ArrayList(productosBusqueda)
    private lateinit var txtSearchView: SearchView

    init {
        //Se está inicializando la lista listaOriginal con los elementos de la lista productosBusqueda
        //Esto se hace para mantener una copia original de la lista de contactos sin filtrar, de modo que cuando se realice
        // un filtrado en la lista principal (productosBusqueda), siempre se pueda restaurar la lista original si es necesario.
        listaOriginal.addAll(productosBusqueda)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoBusquedaViewHolder {
        // Implementa la lógica para crear el ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        //txtSearchView = parent.findViewById(R.id.txtSearchView) as SearchView
        return ProductoBusquedaViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductoBusquedaViewHolder, position: Int) {

        val producto = productosBusqueda[position]
        holder.itemView.findViewById<TextView>(R.id.txtViewNombreProductoBuscador).text = producto.nombre
        holder.itemView.findViewById<TextView>(R.id.txtViewPrecioBuscador).text = producto.precio.toString()
        holder.itemView.visibility = View.VISIBLE // Asegurarse de que la vista esté visible

    }

    override fun getItemCount(): Int {
        return productosBusqueda.size
    }
    inner class ProductoBusquedaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.txtViewNombreProductoBuscador)
        private val precioTextView: TextView = itemView.findViewById(R.id.txtViewPrecioBuscador)

        init {
            itemView.setOnClickListener { view ->
                val context: Context = view.context
                val intent = Intent(context, Buscador1::class.java)
                intent.putExtra("ID", productosBusqueda[adapterPosition].id)
                context.startActivity(intent)
            }
        }
    }
    fun filtrar(query: String) {
        val filteredList = if (query.isEmpty()) {
            listaOriginal.toList() // Restaura la lista original si el texto de búsqueda está vacío
        } else {
            listaOriginal.filter { it.nombre.contains(query, ignoreCase = true) }
        }
        productosBusqueda = filteredList
        notifyDataSetChanged()
    }


}