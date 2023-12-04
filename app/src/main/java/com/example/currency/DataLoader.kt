package com.example.currency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class DataLoader {

    suspend fun loadData(urlString: String): List<String> = withContext(Dispatchers.IO) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.inputStream.use { inputStream ->
                Parser.parseData(inputStream)
            }
        } finally {
            connection.disconnect()
        }
    }
}