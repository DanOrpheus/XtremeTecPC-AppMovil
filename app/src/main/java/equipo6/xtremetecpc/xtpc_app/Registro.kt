package equipo6.xtremetecpc.xtpc_app

import android.content.Intent
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
import com.google.firebase.database.database

class Registro : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var et_nombre : EditText
    lateinit var et_email : EditText
    lateinit var et_password1 : EditText
    lateinit var et_password2 : EditText
    lateinit var btn_crearCuenta : Button
    lateinit var btn_iniciarSesion : Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        et_nombre = findViewById(R.id.et_nombre)
        et_email = findViewById(R.id.et_email)
        et_password1 = findViewById(R.id.et_password1)
        et_password2 = findViewById(R.id.et_password2)
        btn_crearCuenta = findViewById(R.id.btn_crearCuenta)
        btn_iniciarSesion = findViewById(R.id.btn_iniciarSesión)

        auth = Firebase.auth

        database = Firebase.database.reference

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
                                var uid = user?.uid ?:"123"
                                Toast.makeText(
                                    this,
                                    "Se ha registrado correctamente",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                //finish()
                                agregarUsuario(uid,email)

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

        btn_iniciarSesion.setOnClickListener {
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }

    private fun agregarUsuario(uid: String, email: String) {
        var nombre = et_nombre.text.toString()
        val usuario = Usuario(email,nombre)

        if(!nombre.isNullOrEmpty()){
            database.child("usuarios").child(uid).setValue(usuario)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        var intent = Intent(this, Inicio::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Se ha registrado correctamente", Toast.LENGTH_SHORT,).show()
                    }else{
                        Toast.makeText(this, "No se pudo guardar el usuario", Toast.LENGTH_SHORT,).show()
                    }
                }
        }else{
            Toast.makeText(this,"Ingresar datos",Toast.LENGTH_SHORT).show()
        }

    }
}