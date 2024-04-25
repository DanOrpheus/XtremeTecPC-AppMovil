package equipo6.xtremetecpc.xtpc_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class Buscador1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscarproductos)

        val searchView = findViewById<SearchView>(R.id.txtSearchView)

        // Configurar el SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Aquí manejas la acción cuando se envía la consulta de búsqueda (por ejemplo, al presionar Enter)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Aquí manejas la acción cuando el texto de búsqueda cambia
                // Puedes realizar búsquedas en tiempo real aquí
                return false
            }
        })
    }
}