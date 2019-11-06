package com.gamehub.iso

import org.junit.Assert
import org.junit.Test

class PhoneManagerTest {
    @Test
    fun emojiFlag() {
        val expectedUS = Pair("US", "\uD83C\uDDFA\uD83C\uDDF8")
        val expectedKR = Pair("KR", "\uD83C\uDDF0\uD83C\uDDF7")
        val expectedJP = Pair("jp", "\uD83C\uDDEF\uD83C\uDDF5")

        val emojiFlagUs = PhoneManager.getEmojiFlag(expectedUS.first)
        println(emojiFlagUs)
        Assert.assertEquals(expectedUS.second, emojiFlagUs)

        val emojiFlagKr = PhoneManager.getEmojiFlag(expectedKR.first)
        println(emojiFlagKr)
        Assert.assertEquals(expectedKR.second, emojiFlagKr)

        val emojiFlagJp = PhoneManager.getEmojiFlag(expectedJP.first)
        println(emojiFlagJp)
        Assert.assertEquals(expectedJP.second, emojiFlagJp)
    }
}