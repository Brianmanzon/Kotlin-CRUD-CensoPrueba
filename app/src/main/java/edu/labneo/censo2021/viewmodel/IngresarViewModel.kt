package edu.labneo.censo2021.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.labneo.censo2021.dao.DbHelper
import edu.labneo.censo2021.model.Persona

class IngresarViewModel: ViewModel() {
    //Llama a la query que guarda la Persona ingresada dentro de la DB.
    fun savePersona(
        nombre: String,
        apellido: String,
        tipoDoc: String,
        doc: String,
        f_nac: String,
        sexo: String,
        direccion: String,
        telefono: String,
        ocupacion: String,
        ingreso: Int,
        context: Context
    ):Boolean {
        val db : DbHelper = DbHelper(context, null)

        return db.savePersona(Persona(0,nombre, apellido, tipoDoc, doc, f_nac, sexo, direccion, telefono, ocupacion, ingreso))

    }
}