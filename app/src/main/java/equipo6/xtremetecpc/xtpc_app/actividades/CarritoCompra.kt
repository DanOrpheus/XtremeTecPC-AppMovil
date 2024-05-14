package equipo6.xtremetecpc.xtpc_app.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import equipo6.xtremetecpc.xtpc_app.Inicio
import equipo6.xtremetecpc.xtpc_app.R
import equipo6.xtremetecpc.xtpc_app.adaptadores.AdaptadorCarrito
import equipo6.xtremetecpc.xtpc_app.adaptadores.adaptadorProducto
import equipo6.xtremetecpc.xtpc_app.bd.Producto

class CarritoCompra : AppCompatActivity() {

    private lateinit var adaptadorCarrito: AdaptadorCarrito
    private var productosCarrito: MutableList<Producto> = mutableListOf()
    private lateinit var txtViewCarritoVacio: TextView
    private lateinit var txtViewSubtotalCantidadCarrito:TextView
    private lateinit var txtViewTotalMonetarioCarrito:TextView

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
        setContentView(R.layout.activity_carrito)
        Log.d("CarritoCompra", "Activity creada")
        //Iniciación del adaptador
        adaptadorCarrito = AdaptadorCarrito()
        Log.d("CarritoCompra", "AdaptadorCarrito inicializado")
        //Iniciación del recycleView
        val recyclerViewCarrito = findViewById<RecyclerView>(R.id.reclycleViewCarrito)
        recyclerViewCarrito.adapter = adaptadorCarrito
        recyclerViewCarrito.layoutManager = LinearLayoutManager(this)

        txtViewCarritoVacio = findViewById(R.id.txtViewCarritoVacio)

        productosCarrito = CarritoManager.obtenerProductos()

        Log.d("CarritoCompra", "Productos observados: ${productosCarrito.joinToString { it.nombre }}")
        if (productosCarrito.isNotEmpty()) {
            adaptadorCarrito.actualizarDatos(productosCarrito)
            txtViewCarritoVacio.visibility = View.GONE
            recyclerViewCarrito.visibility = View.VISIBLE
        } else {
            txtViewCarritoVacio.visibility = View.VISIBLE
            recyclerViewCarrito.visibility = View.GONE
        }

        // Inflar el Spinner
        val spinnerEnvio = findViewById<Spinner>(R.id.spinnerOpcionesEnvio)
        val opcionesEnvio = arrayOf("Seleccione una opción de envío",
            "Recoger en sucursal --- $0.00",
            "Envio A Domicilio (Solo Cd. Obregón) --- $49.00",
            "Envio Estandar 3 a 6 Días --- $104.00",
            "Envio Urgente 1 a 2 Días --- $499.00"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesEnvio)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEnvio.adapter = adapter
        // Configurar un listener para el evento de selección del Spinner si es necesario
        spinnerEnvio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val opcionSeleccionada = opcionesEnvio[position]
                Log.d("CarritoCompra", "Opción seleccionada en Spinner: $opcionSeleccionada")

                if (opcionSeleccionada == "Seleccione una opción de envío") {
                    Toast.makeText(this@CarritoCompra, "Por favor, seleccione una opción de envío", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@CarritoCompra, "Seleccionaste: $opcionSeleccionada", Toast.LENGTH_SHORT).show()
                    // Actualizar el subtotal y total
                    actualizarSubtotalYTotal()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@CarritoCompra, "Por favor, seleccione una opción de envío", Toast.LENGTH_SHORT).show()
            }
        }
        //Inicialización de los txtView para el total y subtotal
         txtViewSubtotalCantidadCarrito = findViewById(R.id.txtViewSubtotalCantidadCarrito)
         txtViewTotalMonetarioCarrito = findViewById(R.id.txtViewTotalMonetarioCarrito)

        actualizarSubtotalYTotal()

        //Este botón elimina el producto de la vista.
        val btnVaciarCarrito = findViewById<Button>(R.id.buttonVaciarCarrito)
        btnVaciarCarrito.setOnClickListener {
            CarritoManager.vaciarCarrito()
            adaptadorCarrito.actualizarDatos(emptyList()) // Limpiar el RecyclerView
            txtViewCarritoVacio.visibility = View.VISIBLE
            recyclerViewCarrito.visibility = View.GONE

        }
        //Este abre la pantalla de Gracias por su compra
        val btnPagar = findViewById<Button>(R.id.btnPagarCarrito)
        btnPagar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Compra realizada")
            builder.setMessage("Gracias por su compra")

            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val intent = Intent(this, Inicio::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }
        // Obtener una referencia al BottomNavigationView del layout
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navmenu)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


    }
    /**
     * Esta función se encarga de usar los métodos calcularSubtotal y calcularTotal para estar actualizando
     * los txtView con la información nueva, como costo de envío y productos.
     */
    fun actualizarSubtotalYTotal() {
        val subtotal = calcularSubtotal()
        val total = calcularTotal()

        Log.d("CarritoCompra", "Subtotal: $subtotal")
        Log.d("CarritoCompra", "Total: $total")


        // Actualizar los TextViews con los nuevos valores
        txtViewSubtotalCantidadCarrito.text = "$ ${String.format("%.2f", subtotal)}"
        txtViewTotalMonetarioCarrito.text = "$ ${String.format("%.2f", total)}"
    }
    /**
     * Este método obtiene la información del spinner y lo transforma a double para calcular el envío
     */
    private fun calcularCostoEnvio(): Double {
        val costoEnvioPorDefecto = 0.0  // Valor predeterminado si no hay productos en el carrito
        Log.d("CarritoCompra", "Que productos hay: $productosCarrito")
        if (productosCarrito.isEmpty()) {
            return costoEnvioPorDefecto
        }

        // Obtener el Spinner y la opción seleccionada
        val spinnerEnvio = findViewById<Spinner>(R.id.spinnerOpcionesEnvio)
        val opcionSeleccionada = spinnerEnvio.selectedItem?.toString()?.trim() ?: return costoEnvioPorDefecto

        Log.d("CarritoCompra", "Opción seleccionada para calcular costo de envío: $opcionSeleccionada")

        // Verificar que no sea la opción por defecto
        val costoEnvio = when {
            opcionSeleccionada.startsWith("Recoger en sucursal") -> 0.0
            opcionSeleccionada.startsWith("Envio A Domicilio") -> 49.0
            opcionSeleccionada.startsWith("Envio Estandar") -> 104.0
            opcionSeleccionada.startsWith("Envio Urgente") -> 499.0
            else -> costoEnvioPorDefecto
        }
        Log.d("CarritoCompra", "Costo de envío calculado: $costoEnvio")
        return costoEnvio
    }

    /**
     * Esta función unicamente calcula el subtotal de los productos, sin incluir el envío
     */
    private fun calcularSubtotal(): Double {
        var subtotal = 0.0

        for (productoCantidadEntry in CarritoManager.obtenerProductosConCantidad()) {
            val producto = productoCantidadEntry.key
            val cantidad = productoCantidadEntry.value
            val precioTotalProducto = producto.precio * cantidad
            subtotal += precioTotalProducto
        }

        Log.d("CarritoCompra", "Subtotal calculado: $subtotal")
        return subtotal
    }
    /**
     * Esta función calcula el total de los productos, sumando la cantidad de los productos(cantidad x precio del producto)
     * y sumandole el envío
     */
    private fun calcularTotal(): Double {
        val costoEnvio = calcularCostoEnvio()
        val subtotal = calcularSubtotal()

        // Calcular el total sumando el subtotal y el costo de envío
        val totalConEnvio = subtotal + costoEnvio

        // Logs adicionales para verificar los valores
        Log.d("CarritoCompra", "Costo de envío utilizado: $costoEnvio")
        Log.d("CarritoCompra", "Subtotal utilizado: $subtotal")
        Log.d("CarritoCompra", "Total con envío calculado: $totalConEnvio")

        return totalConEnvio
    }
    /**
     * Función para poder abrir otra pantalla en el bottomNavigation
     */
    private fun abrirActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
        // Opcional: cierra la actividad actual si deseas
    }
    /**
     * Función para activar el icono de retroceso
     */
    fun retroceder(view: View) {
        //para iconos de retroceder
        onBackPressed()
    }
}