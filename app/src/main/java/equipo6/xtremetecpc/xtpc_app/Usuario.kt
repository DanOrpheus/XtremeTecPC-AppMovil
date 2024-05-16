package equipo6.xtremetecpc.xtpc_app

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario(var email: String, var nombre: String)
