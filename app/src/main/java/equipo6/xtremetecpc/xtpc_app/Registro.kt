package equipo6.xtremetecpc.xtpc_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var et_nombre : EditText
    lateinit var et_email : EditText
    lateinit var et_password1 : EditText
    lateinit var et_password2 : EditText
    lateinit var btn_crearCuenta : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        et_email = findViewById(R.id.et_email)
        et_password1 = findViewById(R.id.et_password1)
        et_password2 = findViewById(R.id.et_password2)
        btn_crearCuenta = findViewById(R.id.btn_crearCuenta)

        auth = Firebase.auth

        btn_crearCuenta.setOnClickListener{
            var email: String = et_email.text.toString()
            var password1: String = et_password1.text.toString()
            var password2: String = et_password2.text.toString()

            if(!email.isNullOrEmpty() && !password1.isNullOrEmpty() && !password2.isNullOrEmpty()){

                if(password1 == password2){

                    auth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                Toast.makeText(
                                    this,
                                    "Se ha registrado correctamente",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                finish()

                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al registrar",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }

                }else{
                    Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this,"Ingresar datos",Toast.LENGTH_SHORT).show()
            }
        }
    }
}