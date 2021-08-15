package edu.labneo.censo2021.dao
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import edu.labneo.censo2021.model.Persona
import java.util.*
import kotlin.collections.ArrayList

class DbHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    companion object {
        private val DATABASE_NAME = "censo2021.db"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "personas"
        val COLUMN_ID = "id"
        val COLUMN_NOMBRE = "nombre"
        val COLUMN_APELLIDO = "apellido"
        val COLUMN_TIPODOC = "tipo_doc"
        val COLUMN_DNI = "documento"
        val COLUMN_FECHA_NAC = "fecha_nac"
        val COLUMN_SEXO = "sexo"
        val COLUMN_DIRECCION = "direccion"
        val COLUMN_TELEFONO = "telefono"
        val COLUMN_OCUPACION = "ocupacion"
        val COLUMN_INGRESO = "ingreso"
    }


    override fun onCreate(db: SQLiteDatabase?) {

        var createTable =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY," +
                    "$COLUMN_NOMBRE TEXT," +
                    "$COLUMN_APELLIDO TEXT," +
                    "$COLUMN_TIPODOC TEXT," +
                    "$COLUMN_DNI TEXT," +
                    "$COLUMN_FECHA_NAC TEXT," +
                    "$COLUMN_SEXO TEXT," +
                    "$COLUMN_DIRECCION TEXT," +
                    "$COLUMN_TELEFONO TEXT," +
                    "$COLUMN_OCUPACION TEXT," +
                    "$COLUMN_INGRESO INTEGER)"

        db?.execSQL(createTable)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }



    fun clearDbAndRecreate() {
        clearDb()
        onCreate(writableDatabase)
    }

    fun clearDb() {
        writableDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
    //Guardo persona
    fun savePersona (persona: Persona): Boolean{
        try{
            val db = this.writableDatabase

            val values = ContentValues()


            values.put(COLUMN_NOMBRE, persona.nombre.capitalize())
            values.put(COLUMN_APELLIDO, persona.apellido.capitalize())
            values.put(COLUMN_TIPODOC, persona.tipoDoc)
            values.put(COLUMN_DNI, persona.doc)
            values.put(COLUMN_FECHA_NAC, persona.fecha_nac)
            values.put(COLUMN_SEXO, persona.sexo)
            values.put(COLUMN_DIRECCION, persona.direccion)
            values.put(COLUMN_TELEFONO, persona.telefono)
            values.put(COLUMN_OCUPACION, persona.ocupacion)
            values.put(COLUMN_INGRESO, persona.ingreso)

            db.insert(TABLE_NAME,null,values)

            return true
        } catch(e: Exception){
            Log.e("ERROR DATABASE", e.message.toString())
            return false
        }
    }

    //Traigo todos ordenados por apellido
    fun getAllPersonas():ArrayList<Persona> {
        val db = this.readableDatabase
        val listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        val query ="SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_APELLIDO ASC;"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){

            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))
                val tipo_doc = cursor.getString(cursor.getColumnIndex(COLUMN_TIPODOC))
                val doc = cursor.getString(cursor.getColumnIndex(COLUMN_DNI))
                val fecha_nac = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_NAC))
                val sexo = cursor.getString(cursor.getColumnIndex(COLUMN_SEXO))
                val direccion = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCION))
                val telefono = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO))
                val ocupacion = cursor.getString(cursor.getColumnIndex(COLUMN_OCUPACION))
                val ingreso = cursor.getInt(cursor.getColumnIndex(COLUMN_INGRESO))

                listaPersonas.add(
                    Persona(
                        id,
                        nombre,
                        apellido,
                        tipo_doc,
                        doc,
                        fecha_nac,
                        sexo,
                        direccion,
                        telefono,
                        ocupacion,
                        ingreso
                    )
                )
            } while (cursor.moveToNext())
        }
        return listaPersonas
    }

    //Modifico por ID
    fun modificarPersona(persona: Persona): Boolean {
        var b: Boolean = false
        try {

            val db = this.writableDatabase

            val values = ContentValues()

            values.put(COLUMN_NOMBRE, persona.nombre)
            values.put(COLUMN_APELLIDO, persona.apellido)
            values.put(COLUMN_TIPODOC, persona.tipoDoc)
            values.put(COLUMN_DNI, persona.doc)
            values.put(COLUMN_FECHA_NAC, persona.fecha_nac)
            values.put(COLUMN_SEXO, persona.sexo)
            values.put(COLUMN_DIRECCION, persona.direccion)
            values.put(COLUMN_TELEFONO, persona.telefono)
            values.put(COLUMN_OCUPACION, persona.ocupacion)
            values.put(COLUMN_INGRESO, persona.ingreso)

            val whereclause = "$COLUMN_ID=?"
            val whereargs = arrayOf(persona.id.toString())

            db.update(TABLE_NAME, values, whereclause, whereargs)
            b = true
        } catch (e: java.lang.Exception) {
            Log.e("Error al modificar", e.message.toString())
        }
        return b

    }
    //Borro por ID
    fun borrarPersona(id:Int): Boolean {
        var b: Boolean = false
        try{
            val db = this.writableDatabase


            val whereclause = "$COLUMN_ID=?"
            val whereargs = arrayOf(id.toString())

            db.delete(TABLE_NAME,whereclause,whereargs)
        }catch (e: java.lang.Exception) {
            Log.e("Error al borrar", e.message.toString())
        }
        return b
    }
    //Traigo todas las personas cuyo ingreso sea menor a 5000
    fun getAllPobres():ArrayList<Persona>{
        val db = this.readableDatabase
        val listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_INGRESO < 5000"



        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){

            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))
                val tipo_doc = cursor.getString(cursor.getColumnIndex(COLUMN_TIPODOC))
                val doc = cursor.getString(cursor.getColumnIndex(COLUMN_DNI))
                val fecha_nac = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_NAC))
                val sexo = cursor.getString(cursor.getColumnIndex(COLUMN_SEXO))
                val direccion = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCION))
                val telefono = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO))
                val ocupacion = cursor.getString(cursor.getColumnIndex(COLUMN_OCUPACION))
                val ingreso = cursor.getInt(cursor.getColumnIndex(COLUMN_INGRESO))

                listaPersonas.add(
                    Persona(
                        id,
                        nombre,
                        apellido,
                        tipo_doc,
                        doc,
                        fecha_nac,
                        sexo,
                        direccion,
                        telefono,
                        ocupacion,
                        ingreso
                    )
                )
            } while (cursor.moveToNext())
        }
        return listaPersonas

    }
    // Utilizo esto luego de sincronizar para eliminar todas las columnas en la DB del teléfono luego de haberlas subido a Firebase
    fun sincronizar(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)

    }
    // Traigo todas las personas cuyo campo de ocupación es vacío
    fun getDesempleados():ArrayList<Persona>{
        val db = this.readableDatabase
        val listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_OCUPACION=''"



        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){

            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDO))
                val tipo_doc = cursor.getString(cursor.getColumnIndex(COLUMN_TIPODOC))
                val doc = cursor.getString(cursor.getColumnIndex(COLUMN_DNI))
                val fecha_nac = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_NAC))
                val sexo = cursor.getString(cursor.getColumnIndex(COLUMN_SEXO))
                val direccion = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCION))
                val telefono = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO))
                val ocupacion = cursor.getString(cursor.getColumnIndex(COLUMN_OCUPACION))
                val ingreso = cursor.getInt(cursor.getColumnIndex(COLUMN_INGRESO))

                listaPersonas.add(
                    Persona(
                        id,
                        nombre,
                        apellido,
                        tipo_doc,
                        doc,
                        fecha_nac,
                        sexo,
                        direccion,
                        telefono,
                        ocupacion,
                        ingreso
                    )
                )
            } while (cursor.moveToNext())
        }
        return listaPersonas


    }

}


