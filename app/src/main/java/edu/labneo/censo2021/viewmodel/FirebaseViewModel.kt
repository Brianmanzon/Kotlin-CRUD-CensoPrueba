package edu.labneo.censo2021.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.labneo.censo2021.dao.DbHelper
import edu.labneo.censo2021.model.Persona

class FirebaseViewModel: ViewModel() {
    //Terminé haciéndole un ViewModel para las cosas de Firebase aparte. No sé si esto estaría bien, o si debería hacerlo dentro del Helper (O hacer simplmente otro archivo dentro de DAO)
    //pushFirebase hace el push y luego llama a la función que borra todo lo de la DB de SQLite luego de pushear
    fun pushFirebase(context: Context){
        val db : DbHelper = DbHelper(context, null)
        val dataList = db.getAllPersonas()
        var firebaseDatabase= FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.getReference()

        for (persona in dataList){
            databaseReference.child("Personas").push().setValue(persona)
        }
        db.sincronizar()

    }

    //Función que verifica que el teléfono tenga conexión a internet, devuelve True o False dependiendo de eso.
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}