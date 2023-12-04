package com.example.currency

import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

object Parser {

    fun parseData(inputStream: InputStream): List<String> {
        val exchangeRates = mutableListOf<String>()
        val dbFactory = DocumentBuilderFactory.newInstance()

        try {
            val dBuilder = dbFactory.newDocumentBuilder()
            val doc = dBuilder.parse(InputSource(inputStream))

            val cubes = doc.getElementsByTagName("Cube")
            for (i in 0 until cubes.length) {
                val cube = cubes.item(i)
                if (cube is Element) {
                    val currency = cube.getAttribute("currency")
                    val rate = cube.getAttribute("rate")
                    if (!currency.isNullOrBlank() && !rate.isNullOrBlank()) {
                        exchangeRates.add("$currency - $rate")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return exchangeRates
    }
}