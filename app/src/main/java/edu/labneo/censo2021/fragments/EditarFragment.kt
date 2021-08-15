package com.android.viewpager2tabsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.labneo.censo2021.R
import edu.labneo.censo2021.adapters.PersonasAdapter
import edu.labneo.censo2021.viewmodel.VerViewModel
import kotlinx.android.synthetic.main.editar_fragment.*


class EditarFragment : Fragment() {


    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editar_fragment, container)
    }

    companion object {
        fun create(): EditarFragment {
            return EditarFragment()
        }
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Levanto todas las personas ordenadas por apellido, dándole la opción de editar y borrar.
        var verVM: VerViewModel = ViewModelProvider(this).get(VerViewModel::class.java)
        var rv_personas:RecyclerView = view.findViewById(R.id.rv_personas)
        rv_personas.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rv_personas.adapter = PersonasAdapter(verVM.getAllPersona(view.context))


    }


}