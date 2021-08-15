package com.android.viewpager2tabsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import edu.labneo.censo2021.R
import edu.labneo.censo2021.adapters.PersonasAdapter
import edu.labneo.censo2021.viewmodel.ConsultasViewModel

class ConsultasFragment : Fragment() {
    //Le doy al usuario la posibilidad de también editar y borrar dentro de las consultas también.
    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.consultas_fragment, container)
    }

    companion object {
        fun create(): ConsultasFragment {
            return ConsultasFragment()
        }
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var desocupados: Button = view.findViewById(R.id.c_b_desocupados)
        var pobres: Button = view.findViewById(R.id.c_b_pobres)
        val generos: Button = view.findViewById(R.id.i_b_borrar)
        val hombres: TextInputEditText = view.findViewById(R.id.c_t_hombres)
        val mujeres: TextInputEditText = view.findViewById(R.id.c_t_mujeres)
        val otros: TextInputEditText = view.findViewById(R.id.c_t_otros)
        val hombres2: TextInputLayout = view.findViewById(R.id.tf_hombres)
        val mujeres2: TextInputLayout = view.findViewById(R.id.tf_mujeres)
        val otros2: TextInputLayout = view.findViewById(R.id.tf_otros)
        hombres.visibility = INVISIBLE
        hombres2.visibility = INVISIBLE
        mujeres.visibility = INVISIBLE
        mujeres2.visibility = INVISIBLE
        otros.visibility = INVISIBLE
        otros2.visibility = INVISIBLE

        desocupados.setOnClickListener{
            //Levanto los desocupados mayores de edad en el RV
            var consultasVM: ConsultasViewModel = ViewModelProvider(this).get(ConsultasViewModel::class.java)
            var rv_consultas: RecyclerView = view.findViewById(R.id.c_rv_consultas)
            rv_consultas.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
            rv_consultas.adapter = PersonasAdapter(consultasVM.desocupados(view.context))


        }
        pobres.setOnClickListener{
            //Levanto la gente debajo de la linea de pobreza en el RV
            var consultasVM: ConsultasViewModel = ViewModelProvider(this).get(ConsultasViewModel::class.java)
            var rv_consultas: RecyclerView = view.findViewById(R.id.c_rv_consultas)
            rv_consultas.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
            rv_consultas.adapter = PersonasAdapter(consultasVM.pobres(view.context))


        }
        //Hago los texts visibles o invisibles dependiendo de como estaban al clickearlos.
        generos.setOnClickListener{
            if (hombres.visibility == INVISIBLE){
                hombres.visibility = VISIBLE
                hombres2.visibility = VISIBLE
                mujeres.visibility = VISIBLE
                mujeres2.visibility = VISIBLE
                otros.visibility = VISIBLE
                otros2.visibility = VISIBLE

            } else if (hombres.visibility == VISIBLE) {
                hombres.visibility = INVISIBLE
                hombres2.visibility = INVISIBLE
                mujeres.visibility = INVISIBLE
                mujeres2.visibility = INVISIBLE
                otros.visibility = INVISIBLE
                otros2.visibility = INVISIBLE
            }
            var consultasVM: ConsultasViewModel = ViewModelProvider(this).get(ConsultasViewModel::class.java)

            val listaPorcentajes = consultasVM.generos(view.context)
            hombres.setText("Hombres: "+ listaPorcentajes[0] + "%")
            mujeres.setText("Mujeres: "+ listaPorcentajes[1] + "%")
            otros.setText("Otros: "+ listaPorcentajes[2] + "%")


        }
    }
}