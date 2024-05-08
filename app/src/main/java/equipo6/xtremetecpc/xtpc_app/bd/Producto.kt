package equipo6.xtremetecpc.xtpc_app.bd

import java.io.Serializable

data class Producto(var id: Int? = null, val imagenurl: String, val nombre: String, val precio: Double, val stock: Int, val descripcion:String): Serializable
{
    constructor() : this(0, "", "", 0.0, 0, "")
}
val Producto.formattedPrice: String
    get() = "$${String.format("%.2f", precio)}"