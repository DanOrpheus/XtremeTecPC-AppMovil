package equipo6.xtremetecpc.xtpc_app.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import equipo6.xtremetecpc.xtpc_app.Producto
import equipo6.xtremetecpc.xtpc_app.R

class adaptadorProducto(private val context: Context, private val productList: List<Producto>) : RecyclerView.Adapter<adaptadorProducto.ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productList[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.txtViewNombreProductoBuscador)
        private val precioTextView: TextView = itemView.findViewById(R.id.txtViewPrecioBuscador)

        fun bind(producto: Producto) {
            nombreTextView.text = producto.nombre
            precioTextView.text = "Precio: ${producto.precio}"
        }
    }
}