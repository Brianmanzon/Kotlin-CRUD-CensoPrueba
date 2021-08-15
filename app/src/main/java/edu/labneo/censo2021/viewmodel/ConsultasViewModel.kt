package edu.labneo.censo2021.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import edu.labneo.censo2021.dao.DbHelper
import edu.labneo.censo2021.model.Persona
import java.util.*
import kotlin.collections.ArrayList

class ConsultasViewModel: ViewModel() {
    //Chanchada. Debido al hecho de que el SQLite not tiene un tipo de datos para fecha, tuve que trabajar con String
    //Y se me hizo imposible poder calcular la edad dentro de la misma query
    //Por esa razón trabajo acá el String para corroborar que la persona tenga 18 años (Tampoco tiene en cuenta los meses o días)
    //Idealmente hubiese puesto un check box en el ingreso, y un campo en la tabla que me indicase si la persona es mayor de edad o no, pero creo que hacer eso
    //Mataba un pooc la idea de la consigna, así que no lo hice.
    //Finalmente retorna una lista con todos los mayores desempleados, teniendo en cuenta que la query ya trae los desempleados.
    //Otra cosa es que no sé si realmente ese tipo de cosas se hacen acá o en el DbHelper, considerando que también estoy trabajando con las listas que se traen de la DB, pero no con querys.
    //Finalmente lo dejé acá y en el DbHelper dejé puramente querys.
    fun desocupados(context: Context): ArrayList<Persona>{
        val db: DbHelper = DbHelper(context, null)


        var listaPersonas = db.getDesempleados()
        var listaDesocupados: ArrayList<Persona> = ArrayList<Persona>()

        for (persona in listaPersonas){
            val año = Calendar.getInstance().get(Calendar.YEAR) - 18
            val edad = persona.fecha_nac.takeLast(4).toInt()
            if(edad<=año){
                listaDesocupados.add(persona)
            }
        }
        return listaDesocupados
    }

    //Traig los pobres.
    fun pobres(context:Context):ArrayList<Persona>{
        val db: DbHelper = DbHelper(context, null)

        return db.getAllPobres()
    }
    //Traigo todos y hago el cálculo para sacar los % de los géneros
    //Nuevamente, no sé si esto iría acá o en el DbHelper, pero finalmente decidí dejarlo acá, sino sería simplemente mover el código allá y traerme directamente los porcentajes de allí
    fun generos(context:Context):ArrayList<Int>{
        val db: DbHelper = DbHelper(context, null)
        val listaPersonas = db.getAllPersonas()
        var masc = 0
        var fem = 0
        var otro = 0
        var listaPorcentajes: ArrayList<Int> = ArrayList<Int>()
        if (listaPersonas.size!=0){
            val total = listaPersonas.size
            for (persona in listaPersonas){
                when (persona.sexo){
                    "Masculino" -> masc = masc + 1
                    "Femenino" -> fem = fem + 1
                    "Otro" -> otro = otro + 1

                 }

            }
            var mascPor = ((masc*100)/total)
            var femPor = ((fem*100)/total)
            var otroPor = ((otro*100)/total)
            listaPorcentajes.add(mascPor)
            listaPorcentajes.add(femPor)
            listaPorcentajes.add(otroPor)
            return listaPorcentajes
        } else{
            var mascPor = 0
            var femPor = 0
            var otroPor = 0
            listaPorcentajes.add(mascPor)
            listaPorcentajes.add(femPor)
            listaPorcentajes.add(otroPor)
            return listaPorcentajes
        }


    }

}