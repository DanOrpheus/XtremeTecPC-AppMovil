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
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var btn_login : Button
    lateinit var btn_registo : Button
    lateinit var et_email_login : EditText
    lateinit var et_password_login : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        btn_login = findViewById(R.id.btn_login)
        btn_registo = findViewById(R.id.btn_registro)
        et_email_login = findViewById(R.id.et_email_login)
        et_password_login = findViewById(R.id.et_password_login)

        btn_registo.setOnClickListener{
            var intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener{

            var email: String = et_email_login.text.toString()
            var password: String = et_password_login.text.toString()

            if(!email.isNullOrEmpty() && !password.isNullOrEmpty()){

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            var intent = Intent(this, Inicio::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

            }else{
                Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
            }
            
        }

    }
}