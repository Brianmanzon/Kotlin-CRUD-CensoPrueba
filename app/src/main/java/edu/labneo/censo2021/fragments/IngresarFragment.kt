package com.android.viewpager2tabsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.labneo.censo2021.R
import edu.labneo.censo2021.fragments.DatePickerFragment
import edu.labneo.censo2021.viewmodel.*


class IngresarFragment : Fragment() {

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ingresar_fragment, container)
    }

    companion object {
        fun create(): IngresarFragment {
            return IngresarFragment()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val ingresarVM: IngresarViewModel = ViewModelProvider(this).get(IngresarViewModel::class.java)
        val firebaseVM: FirebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        val verVM: VerViewModel = ViewModelProvider(this).get(VerViewModel::class.java)
        val nombre: TextInputEditText = view.findViewById(R.id.i_t_nombre)
        val apellido: TextInputEditText = view.findViewById(R.id.i_t_apellido)
        val docTexto :TextInputEditText = view.findViewById(R.id.i_t_doc)
        val docSpinner: Spinner = view.findViewById(R.id.i_s_doc)
        val documentos = listOf("DNI", "CI", "LC","LE","OTRO")
        docSpinner.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, documentos)
        val f_nac : TextInputEditText = view.findViewById(R.id.i_t_nac)
        val sexoSpinner: Spinner =view.findViewById(R.id.i_s_sex)
        val sexo= listOf("Masculino","Femenino","Otro")
        val direccion : TextInputEditText = view.findViewById(R.id.i_t_direccion)
        val telefono : TextInputEditText = view.findViewById(R.id.i_t_tel)
        val ocupacion: TextInputEditText = view.findViewById(R.id.i_t_ocupacion)
        val ingreso: TextInputEditText = view.findViewById(R.id.i_t_ingreso)
        val ingresar : Button = view.findViewById(R.id.i_b_ingresar)
        val borrar : Button = view.findViewById(R.id.i_b_borrar)
        val sincronizar: Button = view.findViewById(R.id.i_b_sincronizar)
        sexoSpinner.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, sexo)
        var docSeleccionado = ""
        var sexoSeleccionado = ""

        docSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(context,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                docSeleccionado= documentos[position]
            }

        }

        sexoSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(context,"no hay items seleccionados", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sexoSeleccionado= sexo[position]
            }

        }



        f_nac.setOnClickListener {
            fun onDateSelected(day: Int, month: Int, year: Int) {
                    f_nac.setText("$day/$month/$year")}
                fun showDatePickerDialog() {
                    val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
                    datePicker.show(childFragmentManager, "datePicker")
                }
            showDatePickerDialog()
                }

        borrar.setOnClickListener{
            //Con este botón dejo todos los campos en blanco
            nombre.setText("")
            apellido.setText("")
            docTexto.setText("")
            f_nac.setText("")
            direccion.setText("")
            telefono.setText("")
            ocupacion.setText("")
            ingreso.setText("")

        }

        ingresar.setOnClickListener{
            //Corroboro que los campos obligatorios estén llenados correctamente.
            if(nombre.text!!.isNotEmpty() && apellido.text!!.isNotEmpty() && docSeleccionado.isNotEmpty() && docTexto.text!!.isNotEmpty() && f_nac.text!!.isNotEmpty() && sexoSeleccionado.isNotEmpty() && ingreso.text!!.isNotEmpty()){
                ingresarVM.savePersona(nombre.text.toString(),
                    apellido.text.toString(),
                    docSeleccionado,
                    docTexto.text.toString(),
                    f_nac.text.toString(),
                    sexoSeleccionado,
                    direccion.text.toString(),
                    telefono.text.toString(),
                    ocupacion.text.toString(),
                    ingreso.text.toString().toInt(), it.context)
                //Chanchada que me permite poder "actualizar" el fragmento que tiene todas las personas ordenadas por apellido. Tengo entendido que esto se hace con observers, pero realmente no llegué a poder aprenderlo/aplicarlo
                //al final también vacío todos los campos.
                activity?.recreate()
                nombre.setText("")
                apellido.setText("")
                docTexto.setText("")
                f_nac.setText("")
                direccion.setText("")
                telefono.setText("")
                ocupacion.setText("")
                ingreso.setText("")


                Toast.makeText(it.context, "Persona guardada!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(it.context,"Debe completar los campos obligatorios!", Toast.LENGTH_LONG).show()
            }
        }

        sincronizar.setOnClickListener{
            //Sincroniza con Firebase. Verifica que la DB no esté vacía, luego que el teléfono tenga conexión (ya sea por WiFi, internet móvil o Ethernet, y hace el push.
            // Esto no es la mejor solución ya que me vi obligado a borrar todo lo de la DB de SQLite en el teléfono tras pushear, puesto que de no hacerlo, al pushear de nuevo quedarían
            // Registros dobles en Firebase. Y además, considerando que las consultas se hacen hacía la base de SQLite y no a Firebase, Firebase se usaría solamente  como guardado una vez terminado el censo
            // Idealmente se migraría todo a Firebase, haciendo también las consultas ahí. En este caso lo agregué solamente para cumplir con la consigna de que la app te permitisiese levantar los datos a alguien servicio web.
            if(verVM.getAllPersona(it.context).size > 0){
                if(firebaseVM.isOnline(it.context)){
                    firebaseVM.pushFirebase(it.context)
                    activity?.recreate()
                    Toast.makeText(it.context,"DB sincronizada a Firebase!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(it.context, "Debe tener conexión a internet para sincronizar!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(it.context, "La base de datos está vacía!", Toast.LENGTH_SHORT).show()
            }


        }
        }
    }




