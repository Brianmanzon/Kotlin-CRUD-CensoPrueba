package edu.labneo.censo2021.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.labneo.censo2021.R
import edu.labneo.censo2021.fragments.DatePickerFragment
import edu.labneo.censo2021.model.Persona
import edu.labneo.censo2021.viewmodel.EditarViewModel
import edu.labneo.censo2021.viewmodel.IngresarViewModel

class PersonaModificarActivity : AppCompatActivity() {


    lateinit var nombre: TextInputEditText
    lateinit var apellido: TextInputEditText
    lateinit var docTexto : TextInputEditText
    lateinit var docSpinner: Spinner
    val documentos = listOf("DNI", "CI", "LC","LE","OTRO")

    lateinit var f_nac : TextInputEditText
    lateinit var sexoSpinner: Spinner
    val sexo= listOf("Masculino","Femenino","Otro")
    lateinit var direccion : TextInputEditText
    lateinit var telefono : TextInputEditText
    lateinit var ocupacion: TextInputEditText
    lateinit var ingreso: TextInputEditText
    lateinit var modificar : Button
    lateinit var borrar : Button
    lateinit var editarVM: EditarViewModel
    var docSeleccionado = ""
    var sexoSeleccionado = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona_modificar)

        initialize()
        initializeSpinners()
        mapper()
        borrar()
        date()
        modificar()



        docSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                docSeleccionado= documentos[position]
            }

        }

        sexoSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sexoSeleccionado= sexo[position]
            }

        }





    }
    private fun initialize(){
        editarVM = ViewModelProvider(this).get(EditarViewModel::class.java)
        nombre = findViewById(R.id.m_t_nombre)
        apellido = findViewById(R.id.m_t_apellido)
        docTexto = findViewById(R.id.m_t_doc)
        docSpinner = findViewById(R.id.m_s_doc)
        f_nac = findViewById(R.id.m_t_nac)
        sexoSpinner = findViewById(R.id.m_s_sex)
        direccion = findViewById(R.id.m_t_direccion)
        telefono = findViewById(R.id.m_t_tel)
        ocupacion = findViewById(R.id.m_t_ocupacion)
        ingreso = findViewById(R.id.m_t_ingreso)
        modificar = findViewById(R.id.m_b_modificar)
        borrar = findViewById(R.id.m_b_borrar)


    }
    private fun initializeSpinners(){
        docSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, documentos)
        sexoSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexo)
    }
    private fun mapper(){
        nombre.setText(intent.getStringExtra("nombre"))
        apellido.setText(intent.getStringExtra("apellido"))
        docTexto.setText(intent.getStringExtra("doc"))
        f_nac.setText(intent.getStringExtra("f_nac"))
        direccion.setText(intent.getStringExtra("direccion"))
        telefono.setText(intent.getStringExtra("telefono"))
        ocupacion.setText(intent.getStringExtra("ocupacion"))
        ingreso.setText(intent.getStringExtra("ingreso"))
        val sex = intent.getStringExtra("sexo")
        val doc = intent.getStringExtra("tipoDoc")
        //Tengo estos When para poder settear correctamente los spinners dependiendo de los datos que vengan de la DB.
        when (sex) {
            "Masculino" -> sexoSpinner.setSelection(0)
            "Femenino" ->  sexoSpinner.setSelection(1)
            "Otro" -> sexoSpinner.setSelection(2)
        }
        when (doc) {
            "DNI" -> docSpinner.setSelection(0)
            "CI" ->  docSpinner.setSelection(1)
            "LC" -> docSpinner.setSelection(2)
            "LE" -> docSpinner.setSelection(3)
            "OTRO" -> docSpinner.setSelection(4)
        }


    }
    private fun borrar(){
        //Simplemente ejecuta la Query de borrar dependiendo del ID, y me manda a la Main Activity nuevamente.
        borrar.setOnClickListener{
            editarVM.borrarPersona(intent.getStringExtra("id").toString().toInt(), this)
            Toast.makeText(it.context, "Persona borrada!", Toast.LENGTH_SHORT).show()
            onBackPressed()


        }

    }
    private fun date(){

        f_nac.setOnClickListener {
            fun onDateSelected(day: Int, month: Int, year: Int) {
                f_nac.setText("$day/$month/$year")}
            fun showDatePickerDialog() {
                val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
                datePicker.show(this.supportFragmentManager.beginTransaction(), "datePicker")
            }
            showDatePickerDialog()
        }
    }
    private fun modificar() {
        //Lo mismo que borrar, pero modifica mediante ID, y luego me manda a la MainActivity. También verifica que los cambios obligatorios estén correctamente llenos.
        modificar.setOnClickListener {
            val id= intent.getStringExtra("id").toString().toInt()


            if (nombre.text!!.isNotEmpty() && apellido.text!!.isNotEmpty() && docSeleccionado.isNotEmpty() && docTexto.text!!.isNotEmpty() && f_nac.text!!.isNotEmpty() && sexoSeleccionado.isNotEmpty() && ingreso.text!!.isNotEmpty()) {
                editarVM.modificoPersona(
                    id,
                    nombre.text.toString(),
                    apellido.text.toString(),
                    docSeleccionado,
                    docTexto.text.toString(),
                    f_nac.text.toString(),
                    sexoSeleccionado,
                    direccion.text.toString(),
                    telefono.text.toString(),
                    ocupacion.text.toString(),
                    ingreso.text.toString().toInt(), this
                )
                Toast.makeText(it.context, "Persona modificada!", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }else{
                Toast.makeText(it.context,"Debe completar los campos obligatorios!", Toast.LENGTH_LONG).show()
            }
        }

    }
}