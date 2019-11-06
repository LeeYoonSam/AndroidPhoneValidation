package com.gamehub.iso

import org.junit.Assert
import org.junit.Test
import java.util.*

class CountriesTest {
    @Test
    fun displayNameTest() {
        val countryAF = Countries.COUNTRIES[0]

        val locale = Locale("", countryAF.iso2)
        println("${locale.displayName}, ${locale.country}, ${countryAF.dialCode}")

        Assert.assertEquals(93, countryAF.dialCode)
        Assert.assertEquals("AF", locale.country)
    }
}