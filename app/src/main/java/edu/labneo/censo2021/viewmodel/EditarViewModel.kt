package edu.labneo.censo2021.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.labneo.censo2021.dao.DbHelper
import edu.labneo.censo2021.model.Persona

class EditarViewModel: ViewModel() {
    //Llamo la query para modificar persona mediante ID
    fun modificoPersona(
        id: Int,
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
        context: Context) {

        val db: DbHelper = DbHelper(context, null)

        db.modificarPersona(Persona(id, nombre, apellido, tipoDoc, doc, f_nac, sexo, direccion, telefono, ocupacion, ingreso))
    }
    //Llamo la query para borrar persona mediante ID
    fun borrarPersona(
        id: Int,
        context: Context) {

        val db: DbHelper = DbHelper(context, null)

        db.borrarPersona(id)
    }

}