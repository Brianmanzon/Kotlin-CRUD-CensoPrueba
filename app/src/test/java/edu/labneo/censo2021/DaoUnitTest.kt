package edu.labneo.censo2021

import edu.labneo.censo2021.dao.DbHelper

import edu.labneo.censo2021.model.Persona
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)

class GetAllPersonasTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbGetAllPersonas() {

        // Given
        val persona: Persona = Persona(1, "Brian", "Manzon","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",4000)
        val persona2: Persona = Persona(2, "Alexis", "Manzon","CI","12312312","20/8/2015","Masculino","Los tilos 725","1122552503","",4000)
        val persona3: Persona = Persona(3, "Luis", "Manzon","LC","42351235","25/11/1983","Masculino","Los tilos 725","1122552503","",4000)
        val persona4: Persona = Persona(4, "Estela", "Masotta","DNI","5234523","1/2/1920","Femenino","Los tilos 725","1122552503","",4000)
        val persona5: Persona = Persona(5, "Nadia", "Alvarez","DNI","544325445","10/10/1990","Masculino","Los tilos 725","1122552503","",4000)
        var listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        listaPersonas.add(persona5)
        listaPersonas.add(persona)
        listaPersonas.add(persona2)
        listaPersonas.add(persona3)
        listaPersonas.add(persona4)
        dbHelper.savePersona(persona)
        dbHelper.savePersona(persona2)
        dbHelper.savePersona(persona3)
        dbHelper.savePersona(persona4)
        dbHelper.savePersona(persona5)


        // When

        val getAllPersonas = dbHelper.getAllPersonas()

        // Then
        assertEquals(getAllPersonas, listaPersonas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)

class SavePersonaTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbSavePersona() {

        // Given
        val persona: Persona = Persona(1, "Brian", "Manzon","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",4000)
        var listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        listaPersonas.add(persona)



        // When
        dbHelper.savePersona(persona)

        // Then
        assertEquals(dbHelper.getAllPersonas(), listaPersonas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)

class ModificarPersonaTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbModificarPersona() {

        // Given
        val persona: Persona = Persona(1, "Brian", "Manzon","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",4000)
        val personaEditada: Persona = Persona(1, "EditBrian", "EditManzon","DNI","38346604","13/5/1994","Masculino","Asdasdasd","1122552503","Desarrollador",99999)
        var listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        listaPersonas.add(personaEditada)
        dbHelper.savePersona(persona)




        // When
        dbHelper.modificarPersona(personaEditada)

        // Then
        assertEquals(dbHelper.getAllPersonas(), listaPersonas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}


@RunWith(RobolectricTestRunner::class)

class BorrarPersonaTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbBorrarPersona() {

        // Given
        val persona: Persona = Persona(1, "Brian", "Manzon","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",4000)
        var listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        dbHelper.savePersona(persona)
        val id = 1




        // When
        dbHelper.borrarPersona(id)

        // Then
        assertEquals(dbHelper.getAllPersonas().size, 0)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}

@RunWith(RobolectricTestRunner::class)

class GetAllPobresTest{

    lateinit var dbHelper: DbHelper

    @Before
    fun setup() {
        dbHelper = DbHelper(RuntimeEnvironment.application, null)
        dbHelper.clearDbAndRecreate()
    }

    @Test
    @Throws(Exception::class)
    fun testDbGetAllPobres() {

        // Given
        val persona: Persona = Persona(1, "Brian", "Manzon","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",4000)
        val persona2: Persona = Persona(2, "Brian2", "Manzon2","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",7000)
        val persona3: Persona = Persona(3, "Brian3", "Manzon3","DNI","38346604","13/5/1994","Masculino","Los tilos 725","1122552503","",4000)
        var listaPersonas: ArrayList<Persona> = ArrayList<Persona>()
        dbHelper.savePersona(persona)
        dbHelper.savePersona(persona2)
        dbHelper.savePersona(persona3)

        listaPersonas.add(persona)
        listaPersonas.add(persona3)





        // When
        val pobres = dbHelper.getAllPobres()

        // Then
        assertEquals(pobres, listaPersonas)
    }

    @After
    fun tearDown() {
        dbHelper.clearDb()
    }
}




