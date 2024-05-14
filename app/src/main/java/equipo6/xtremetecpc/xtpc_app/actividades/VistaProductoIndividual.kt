package equipo6.xtremetecpc.xtpc_app.actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import equipo6.xtremetecpc.xtpc_app.Inicio
import equipo6.xtremetecpc.xtpc_app.R
import equipo6.xtremetecpc.xtpc_app.adaptadores.AdaptadorCarrito
import equipo6.xtremetecpc.xtpc_app.bd.Producto

class VistaProductoIndividual : AppCompatActivity() {

    private lateinit var adaptadorCarrito: AdaptadorCarrito
    private lateinit var productosCarrito: MutableList<Producto>


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
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_producto_individual)

        // Obtener una referencia al BottomNavigationView del layout
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navmenu)
        // Configurar el listener para manejar los clics en los elementos del menú
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val producto: Producto? = intent.getSerializableExtra("producto") as? Producto

        /// Verificar si el producto no es nulo
        if (producto != null) {
            // Utilizar los datos del producto para mostrar los detalles en la actividad
            mostrarDetallesProducto(producto)

            // Configurar el botón para añadir al carrito
            val btnAgregarCarrito = findViewById<Button>(R.id.btnAgregarCarrito)
            btnAgregarCarrito.setOnClickListener {
                CarritoManager.agregarProducto(producto)

                Log.e("VistaProductoIndividual", "El producto se ha añadido.")
                Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show()

                // Navegar a la pantalla del carrito

                val intent = Intent(this, CarritoCompra::class.java)
                startActivity(intent)
            }
        } else {
            Log.e("VistaProductoIndividual", "El producto es null. No se puede mostrar detalles.")
            Toast.makeText(this, "Error al cargar los detalles del producto", Toast.LENGTH_SHORT).show()
        }


    }


    private fun agregarAlCarrito(producto: Producto) {
        CarritoManager.agregarProducto(producto)
        Log.d("VistaProductoIndividual", "Producto agregado: ${producto.nombre}")
    }

    private fun mostrarDetallesProducto(producto: Producto) {
        val nombretxtView=findViewById<TextView>(R.id.txtViewNombreProductoVista)
        val preciotxtView=findViewById<TextView>(R.id.txtViewPrecioProductoVista)
        val descripciontxtView=findViewById<TextView>(R.id.txtViewDescripcionProductoVista)
        val imageView=findViewById<ImageView>(R.id.imagenProductoVista)
        val stocktxtView=findViewById<TextView>(R.id.txtViewCantidadStockVista)

        if(imageView!=null&& nombretxtView != null && preciotxtView != null && descripciontxtView != null && stocktxtView != null)
            Glide.with(this)
                .load(producto.imagenurl)
                .into(imageView)

            nombretxtView.text=producto.nombre
            preciotxtView.text="$${producto.precio}"
            descripciontxtView.text=producto.descripcion
            stocktxtView.text=producto.stock.toString()
    }


    // Función para abrir una actividad específica
    private fun abrirActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
        // Opcional: cierra la actividad actual si deseas
    }
    fun retroceder(view: View) {
        //para iconos de retroceder
        onBackPressed()
    }
}