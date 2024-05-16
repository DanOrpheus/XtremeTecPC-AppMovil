package equipo6.xtremetecpc.xtpc_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Contacto : AppCompatActivity() {

    lateinit var btn_cerrarSesion : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_cerrarSesion = findViewById(R.id.btn_cerrarSesion)

        btn_cerrarSesion.setOnClickListener {
            finish()
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }
}