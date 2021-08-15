package edu.labneo.censo2021

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import edu.labneo.censo2021.view.PersonaModificarActivity
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import androidx.test.espresso.matcher.ViewMatchers.withId as withId1

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */@RunWith(AndroidJUnit4::class)
class UserInstrumentedTest {


    @get:Rule
    var activityRule: ActivityTestRule<PersonaModificarActivity> = ActivityTestRule(PersonaModificarActivity::class.java)


    @Test
    fun EditarTextos(){
        Espresso.onView(ViewMatchers.withId(R.id.m_t_nombre)).perform(typeText("Brian"))
        Espresso.onView(ViewMatchers.withId(R.id.m_t_apellido)).perform(typeText("Manzon"))
        Espresso.onView(ViewMatchers.withId(R.id.m_t_doc)).perform(typeText("38346604"))
        Espresso.onView(ViewMatchers.withId(R.id.m_t_ocupacion)).perform(typeText("Desarrollador"))
        Espresso.onView(ViewMatchers.withId(R.id.m_t_direccion)).perform(typeText("Los tilos 725"))
        Espresso.onView(ViewMatchers.withId(R.id.m_t_tel)).perform(typeText("1122552503"))
        Espresso.onView(ViewMatchers.withId(R.id.m_t_ingreso)).perform(typeText("9999"))



    }

}




