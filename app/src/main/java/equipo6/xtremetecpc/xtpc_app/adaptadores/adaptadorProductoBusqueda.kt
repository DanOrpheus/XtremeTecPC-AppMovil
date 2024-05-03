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
import com.bumptech.glide.Glide
import equipo6.xtremetecpc.xtpc_app.Buscador1
import equipo6.xtremetecpc.xtpc_app.Producto
import equipo6.xtremetecpc.xtpc_app.R

import java.util.Locale
import java.util.stream.Collectors


class adaptadorProductoBusqueda (private var productosBusqueda: List<Producto>) : RecyclerView.Adapter<adaptadorProductoBusqueda.ProductoBusquedaViewHolder>() {

    private val listaOriginal: List<Producto> = productosBusqueda.toList()
    //private lateinit var txtSearchView: SearchView

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

        val imageView = holder.itemView.findViewById<ImageView>(R.id.imgViewFotoProductoBusqueda) // Asegúrate de que el ID sea el correcto
        val imageUrl = producto.imagenurl // Suponiendo que tienes la URL de la imagen en tu objeto Producto

        Glide.with(holder.itemView.context) // Contexto del fragmento o actividad
            .load(imageUrl) // Cargar la imagen desde la URL
            .into(imageView) // Colocar la imagen en el ImageView

        holder.itemView.visibility = View.VISIBLE

    }

    override fun getItemCount(): Int {
        return productosBusqueda.size
    }
    inner class ProductoBusquedaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        productosBusqueda = if (query.isEmpty()) {
            listaOriginal.toList() // Restaura la lista original si el texto de búsqueda está vacío
        } else {
            listaOriginal.filter { it.nombre.contains(query, ignoreCase = true) }
        }

        notifyDataSetChanged()
    }


}