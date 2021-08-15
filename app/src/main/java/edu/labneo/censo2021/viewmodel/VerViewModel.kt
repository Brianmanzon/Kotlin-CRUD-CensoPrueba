package edu.labneo.censo2021.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.labneo.censo2021.dao.DbHelper
import edu.labneo.censo2021.model.Persona

class VerViewModel: ViewModel() {
    //Llama la query que me trae todo ordenado por apellido
    fun getAllPersona(context: Context): ArrayList<Persona>{
        val db: DbHelper = DbHelper(context, null)

        return db.getAllPersonas()

    }


}