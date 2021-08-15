package edu.labneo.censo2021.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.viewpager2tabsample.ConsultasFragment
import edu.labneo.censo2021.adapters.ResourceStore
import com.google.android.material.tabs.TabLayoutMediator
import edu.labneo.censo2021.R
import edu.labneo.censo2021.viewmodel.IngresarViewModel
import kotlinx.android.synthetic.main.activity_main.*

public class MainActivity : AppCompatActivity() {
    //El OnRestart me permite recargar los fragmentos luego de volver de la ActivityModificar, y poder mostrar correctamente en la segunda pestaña los datos editados/borrados.
    override fun onRestart() {
        super.onRestart()
        recreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //De aquí hasta el final levanto el ViewPager con sus fragmentos y tabs.
        renderViewPager()
        renderTabLayer()
    }

    private fun renderViewPager() {
        viewpager.adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int): Fragment {
                return ResourceStore.pagerFragments[position]
            }

            override fun getItemCount(): Int {
                return ResourceStore.tabList.size
            }
        }
    }

    private fun renderTabLayer() {
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = getString(ResourceStore.tabList[position])
        }.attach()

    }
}