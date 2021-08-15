package edu.labneo.censo2021.adapters

import com.android.viewpager2tabsample.ConsultasFragment
import com.android.viewpager2tabsample.EditarFragment
import com.android.viewpager2tabsample.IngresarFragment
import edu.labneo.censo2021.R
//PageViewer
interface ResourceStore {
    companion object {
        val tabList = listOf(
            R.string.tab1, R.string.tab2, R.string.tab3
        )
        val pagerFragments = listOf(
            IngresarFragment.create(), EditarFragment.create(), ConsultasFragment.create()
        )
    }
}