package equipo6.xtremetecpc.xtpc_app.actividades

import equipo6.xtremetecpc.xtpc_app.bd.Producto

/**
 * Singleton para poder navegar en la aplicación y la información del carrito pueda ser accedida
 * desde cualquier pantalla
 */
object CarritoManager {
    //private val productosCarrito = mutableListOf<Producto>()
    private val productosCarrito = mutableMapOf<Producto, Int>()

    fun agregarProducto(producto: Producto) {
        if (productosCarrito.containsKey(producto)) {
            val cantidadActual = productosCarrito[producto] ?: 0 // Si el producto ya está en el carrito, aumenta su cantidad
            productosCarrito[producto] = cantidadActual + 1
        } else {
            productosCarrito[producto] = 1 // Si el producto no está en el carrito, lo agrega con cantidad 1
        }
    }

    fun actualizarCantidadProducto(producto: Producto, nuevaCantidad: Int) {
        if (productosCarrito.containsKey(producto)) {
            productosCarrito[producto] = nuevaCantidad // Si el producto está en el carrito, actualiza su cantidad
        }
    }

    fun eliminarProducto(producto: Producto) {
        productosCarrito.remove(producto)
    }

    fun obtenerProductosConCantidad(): Map<Producto, Int> {
        return productosCarrito
    }

    fun obtenerProductos(): MutableList<Producto> {
        return productosCarrito.keys.toMutableList()
    }
    fun vaciarCarrito() {
        productosCarrito.clear()
    }
}