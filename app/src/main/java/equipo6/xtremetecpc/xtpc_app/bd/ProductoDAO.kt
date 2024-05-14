package equipo6.xtremetecpc.xtpc_app.bd

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProductoDAO {
    fun buscarProductos(query: String, callback: (List<Producto>) -> Unit) {
        val referenciaBaseDatos = FirebaseDatabase.getInstance().getReference("producto")


        // Aplicar el filtro de consulta directamente en Firebase
        val consulta = referenciaBaseDatos.orderByChild("nombre").startAt(query).endAt(query + "\uf8ff")
        consulta.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val resultados = mutableListOf<Producto>()
                //resultados.clear() // Limpiar la lista antes de agregar nuevos resultados
                for (productoSnapshot in snapshot.children) {
                    val producto = productoSnapshot.getValue(Producto::class.java)
                    if (producto != null && producto.nombre.contains(query, ignoreCase = true)) {
                        resultados.add(producto)
                    }
                }
                callback(resultados) // Llamar al callback con los resultados
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores de consulta.
            }
        })
    }
}