package edu.labneo.censo2021.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import edu.labneo.censo2021.R
import edu.labneo.censo2021.model.Persona
import edu.labneo.censo2021.view.PersonaModificarActivity

//Adaptador que uso tanto para el RV que me muestra a todos ordenados por apellido, como para las consultas en la tab de consultas
class PersonasAdapter(val lista: ArrayList<Persona>) :
    RecyclerView.Adapter<PersonasAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ver_todos, parent, false)

        return ViewHolder(view)


    }

    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.setText(lista[position].nombre +" "+lista[position].apellido+ " - ID: " + lista[position].id)
        holder.doc.setText(lista[position].tipoDoc+ " : " + lista[position].doc)
        holder.nac.setText("Fecha de nacimiento: "+ lista[position].fecha_nac+ " - Sexo: " + lista[position].sexo)
        holder.direccion.setText("Dirección: " + lista[position].direccion + " - Teléfono: " + lista[position].telefono)
        holder.ocupacion.setText("Ocupación: "+ lista[position].ocupacion + " - Ingreso mensual: $" + lista[position].ingreso)

        holder.editar.setOnClickListener{
            //Envío los datos hacía la activity de modificar. Dentro de esa activity se pueden tanto modificar los datos, como borrar la persona seleccionada
            val intent: Intent= Intent(it.context, PersonaModificarActivity::class.java)
            intent.putExtra("id", lista[position].id.toString())
            intent.putExtra("nombre", lista[position].nombre)
            intent.putExtra("apellido", lista[position].apellido)
            intent.putExtra("tipoDoc", lista[position].tipoDoc)
            intent.putExtra("doc", lista[position].doc)
            intent.putExtra("f_nac", lista[position].fecha_nac)
            intent.putExtra("sexo", lista[position].sexo)
            intent.putExtra("direccion", lista[position].direccion)
            intent.putExtra("telefono", lista[position].telefono)
            intent.putExtra("ocupacion", lista[position].ocupacion)
            intent.putExtra("ingreso", lista[position].ingreso.toString())

            it.context.startActivity(intent)


        }

    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        // Mapeo de los elementos del layout.
        var nombre: TextInputEditText
        var doc: TextInputEditText
        var nac: TextInputEditText
        var direccion: TextInputEditText
        var ocupacion: TextInputEditText
        lateinit var editar: MaterialButton




        init{
            editar = view.findViewById(R.id.v_bt_editar)
            nombre = view.findViewById(R.id.vt_t_nombre)
            doc = view.findViewById(R.id.vt_t_doc)
            nac = view.findViewById(R.id.vt_t_nac)
            direccion = view.findViewById(R.id.vt_t_direccion)
            ocupacion = view.findViewById(R.id.vt_t_ocupacion)
        }



    }
    }