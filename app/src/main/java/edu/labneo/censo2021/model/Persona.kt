package edu.labneo.censo2021.model
import java.io.Serializable
//Data class de Persona. Idealmente tal vez tendría que haber creado una entity a parte para no tener que tener acá el ID.
data class Persona (var id: Int = 0, val nombre: String, val apellido: String, val tipoDoc: String, val doc: String, val fecha_nac: String, val sexo: String,
                    val direccion: String="", val telefono: String="", val ocupacion: String="", val ingreso: Int) :Serializable
