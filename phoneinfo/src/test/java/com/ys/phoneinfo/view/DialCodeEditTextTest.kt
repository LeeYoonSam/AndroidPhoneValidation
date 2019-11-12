package com.ys.phoneinfo.view

import com.ys.phoneinfo.Countries
import com.ys.phoneinfo.Country
import org.junit.Test
import java.util.*

class DialCodeEditTextTest {
    private val items = Countries.COUNTRIES
    lateinit var selectedCountry: Country

    @Test
    fun getIso2() {
        selectedCountry = items[0]
        println(getIso3(selectedCountry.iso2))

        selectedCountry = items[1]
        println(getIso3(selectedCountry.iso2))

        selectedCountry = items[2]
        println(getIso3(selectedCountry.iso2))

        selectedCountry = items[3]
        println(getIso3(selectedCountry.iso2))

        selectedCountry = items[4]
        println(getIso3(selectedCountry.iso2))

        selectedCountry = items[5]
        println(getIso3(selectedCountry.iso2))

        selectedCountry = items[6]
        println(getIso3(selectedCountry.iso2))

    }

    fun getIso3(iso2: String): String {
        val local = Locale("", iso2)
        return local.isO3Country
    }
}