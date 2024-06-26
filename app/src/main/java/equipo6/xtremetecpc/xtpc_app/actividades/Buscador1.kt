package equipo6.xtremetecpc.xtpc_app.actividades

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import equipo6.xtremetecpc.xtpc_app.R
import equipo6.xtremetecpc.xtpc_app.bd.ProductoDAO
import equipo6.xtremetecpc.xtpc_app.adaptadores.adaptadorProductoBusqueda
import equipo6.xtremetecpc.xtpc_app.bd.Producto

class Buscador1 : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var txtSearchView: SearchView
    private lateinit var listaProductos: RecyclerView
    private lateinit var listaArrayProducto: ArrayList<Producto>
    private lateinit var adapter: adaptadorProductoBusqueda
    private val productoDAO = ProductoDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscarproductos)

        txtSearchView= findViewById(R.id.txtSearchView)
        val editText = txtSearchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.WHITE)
        listaProductos= findViewById(R.id.recycleBuscador)
        listaProductos.layoutManager=LinearLayoutManager(this)

        listaArrayProducto = ArrayList()

        // Inicializar el adaptador vacío
        adapter = adaptadorProductoBusqueda(ArrayList())
        listaProductos.adapter = adapter

        val productos = productoDAO.buscarProductos("") { productos ->
            adapter = adaptadorProductoBusqueda(productos)
            listaProductos.adapter = adapter
        }
        txtSearchView.setOnQueryTextListener(this)


    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (::adapter.isInitialized) {
            adapter.filtrar(newText.orEmpty())
        }
        return true
    }
    fun retroceder(view: View) {
        //para iconos de retroceder
        onBackPressed()
    }

}