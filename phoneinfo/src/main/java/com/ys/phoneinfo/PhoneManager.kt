package com.ys.phoneinfo

import java.util.*
import kotlin.collections.HashMap

object PhoneManager {
    private val charUnicode = HashMap<Char, IntArray>()

    init {
        charUnicode['a'] = intArrayOf(0x1F1E6)
        charUnicode['b'] = intArrayOf(0x1F1E7)
        charUnicode['c'] = intArrayOf(0x1F1E8)
        charUnicode['d'] = intArrayOf(0x1F1E9)
        charUnicode['e'] = intArrayOf(0x1F1EA)
        charUnicode['f'] = intArrayOf(0x1F1EB)
        charUnicode['g'] = intArrayOf(0x1F1EC)
        charUnicode['h'] = intArrayOf(0x1F1ED)
        charUnicode['i'] = intArrayOf(0x1F1EE)
        charUnicode['j'] = intArrayOf(0x1F1EF)
        charUnicode['k'] = intArrayOf(0x1F1F0)
        charUnicode['l'] = intArrayOf(0x1F1F1)
        charUnicode['m'] = intArrayOf(0x1F1F2)
        charUnicode['n'] = intArrayOf(0x1F1F3)
        charUnicode['o'] = intArrayOf(0x1F1F4)
        charUnicode['p'] = intArrayOf(0x1F1F5)
        charUnicode['q'] = intArrayOf(0x1F1F6)
        charUnicode['r'] = intArrayOf(0x1F1F7)
        charUnicode['s'] = intArrayOf(0x1F1F8)
        charUnicode['t'] = intArrayOf(0x1F1F9)
        charUnicode['u'] = intArrayOf(0x1F1FA)
        charUnicode['v'] = intArrayOf(0x1F1FB)
        charUnicode['w'] = intArrayOf(0x1F1FC)
        charUnicode['x'] = intArrayOf(0x1F1FD)
        charUnicode['y'] = intArrayOf(0x1F1FE)
        charUnicode['z'] = intArrayOf(0x1F1FF)
    }

    private fun convertHexToSurrogates(value: IntArray): String {
        return String(value, 0, 1)
    }

    fun getEmojiFlag(code: String): String {
        val codes = code.toLowerCase().toCharArray()

        val firstCode = convertHexToSurrogates(charUnicode[codes.first()]!!)
        val lastCode = convertHexToSurrogates(charUnicode[codes.last()]!!)

        return "$firstCode$lastCode"
    }

    fun getLocaleCountryName(code: String): String {
        val locale = Locale("", code)
        return locale.displayName
    }
}