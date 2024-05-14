package equipo6.xtremetecpc.xtpc_app.adaptadores

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import equipo6.xtremetecpc.xtpc_app.R
import equipo6.xtremetecpc.xtpc_app.bd.Producto

import androidx.activity.viewModels
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import equipo6.xtremetecpc.xtpc_app.actividades.CarritoCompra
import equipo6.xtremetecpc.xtpc_app.actividades.CarritoManager

/**
 * Este adaptador para gestionar la vista de productos en el carrito de compras.
 * Este adaptador se utiliza con RecyclerView.
 */
class AdaptadorCarrito: RecyclerView.Adapter<AdaptadorCarrito.ProductoCarritoViewHolder>() {

    private var productosCarrito = mutableListOf<Producto>() // Lista mutable de productos en el carrito
    private var cantidadesProductos = mutableMapOf<Producto, Int>() // Mapa mutable que almacena la cantidad de cada producto en el carrito

    /**
     * Método para crear un nuevo viewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoCarritoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vista_carrito, parent, false)
        return ProductoCarritoViewHolder(view)
    }

    /**
     * Método llamado para mostrar datos en la vista ProductoCarritoViewHolder.
     */
    override fun onBindViewHolder(holder: ProductoCarritoViewHolder, position: Int) {
        val producto = productosCarrito[position]
        holder.bind(producto, cantidadesProductos[producto] ?: 0)
        Log.d("AdaptadorCarrito", "Producto en posición $position vinculado: ${producto.nombre}")
    }

    /**
     * Método para obtener el número total de productos en el carrito.
     */
    override fun getItemCount(): Int {
        return productosCarrito.size
    }

    /**
     * Actualiza los datos del adaptador con una nueva lista de productos.
     */
    fun actualizarDatos(nuevosProductos: List<Producto>) {
        productosCarrito = nuevosProductos.toMutableList()
        notifyDataSetChanged()
    }
    /**
     * ViewHolder para mostrar un producto individual en el carrito.
     */
    inner class ProductoCarritoViewHolder(itemView: View) : ViewHolder(itemView) {
        // Estos son items de la vista del producto en el carrito
        private val nombreProductoTextView: TextView =
            itemView.findViewById(R.id.txtViewNombreProductoCarrito)
        private val precioProductoTextView: TextView =
            itemView.findViewById(R.id.txtViewPrecioProductoCarrito)
        private val precioTotalTextView: TextView =
            itemView.findViewById(R.id.txtViewPrecioVariosCarrito)
        private val txtViewCantidadProductosCarrito: TextView =
            itemView.findViewById(R.id.txtViewCantidadProductosCarrito)
        private val txtViewSumarProductosCarrito: TextView =
            itemView.findViewById(R.id.txtViewSumarProductosCarrito)
        private val txtViewRestarProductosCarrito: TextView =
            itemView.findViewById(R.id.txtViewRestarProductosCarrito)
        private val imageView: ImageView = itemView.findViewById(R.id.imgViewFotoProductoCarrito)

        /**
         * Vincula los datos del producto con los componentes de la vista.
         */
        fun bind(producto: Producto, cantidad: Int) {
            nombreProductoTextView.text = producto.nombre
            precioProductoTextView.text = "$ ${producto.precio}"

            // Muestra la cantidad de productos y el precio total
            val cantidadAUsar = if (cantidad == 0) 1 else cantidad
            txtViewCantidadProductosCarrito.text = cantidadAUsar.toString()

            // Manejar eventos de clic en botones para cambiar la cantidad
            txtViewSumarProductosCarrito.setOnClickListener {
                val nuevaCantidad = cantidadAUsar + 1
                actualizarCantidadYTotal(nuevaCantidad)
            }
            // Manejar eventos de clic en botones para cambiar la cantidad
            txtViewRestarProductosCarrito.setOnClickListener {
                if (cantidadAUsar > 1) {
                    val nuevaCantidad = cantidadAUsar - 1
                    actualizarCantidadYTotal(nuevaCantidad)
                } else {
                    CarritoManager.eliminarProducto(producto)
                }
            }
            val precioTotal = producto.precio * cantidadAUsar
            precioTotalTextView.text = "$ ${String.format("%.2f", precioTotal)}"

            val imageUrl = producto.imagenurl
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(imageView)

            Log.d("AdaptadorCarrito", "Producto vinculado: $producto")
        }

        /**
         * Actualiza la cantidad de productos y el precio total.
         */
        private fun actualizarCantidadYTotal(nuevaCantidad: Int) {

            txtViewCantidadProductosCarrito.text = nuevaCantidad.toString() // Actualizar la cantidad en el TextView

            val producto = productosCarrito[adapterPosition] // Obtener el producto asociado a esta vista

            val nuevoPrecioTotal = producto.precio * nuevaCantidad // Calcular el nuevo precio total

            precioTotalTextView.text = "$ ${String.format("%.2f", nuevoPrecioTotal)}" // Actualizar el precio total en el TextView"


            CarritoManager.actualizarCantidadProducto(producto, nuevaCantidad) // Actualizar la cantidad en el carrito

            (itemView.context as? CarritoCompra)?.actualizarSubtotalYTotal() // Actualizar subtotal y total en la actividad
        }
    }
}
