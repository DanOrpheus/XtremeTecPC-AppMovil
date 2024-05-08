package equipo6.xtremetecpc.xtpc_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import equipo6.xtremetecpc.xtpc_app.actividades.Buscador1
import equipo6.xtremetecpc.xtpc_app.actividades.CarritoCompra


class Inicio : AppCompatActivity() {


    // Listener para manejar los clics en los elementos del menú
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btm_nav_inicio -> {
                    // Cuando se hace clic en el primer elemento del menú, abrir Activity1
                    abrirActividad(Inicio::class.java)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.btm_nav_buscar -> {
                    // Cuando se hace clic en el segundo elemento del menú, abrir Activity2
                    abrirActividad(Buscador1::class.java)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.btm_nav_carrito ->{
                    abrirActividad(CarritoCompra::class.java)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        // Obtener una referencia al BottomNavigationView del layout
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navmenu)
        // Configurar el listener para manejar los clics en los elementos del menú
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
    // Función para abrir una actividad específica
    private fun abrirActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
         // Opcional: cierra la actividad actual si deseas
    }
}

