package equipo6.xtremetecpc.xtpc_app

data class Producto(var id: Int? = null, val nombre: String, val precio: Double)
{
    constructor() : this(0, "", 0.0)
}
