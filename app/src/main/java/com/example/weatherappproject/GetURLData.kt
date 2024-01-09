package com.example.weatherappproject

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class GetURLData(private val realState: MutableState<String>,
    private val cloudStateArg: MutableState<String>,
    private val likeState: MutableState<String>,
    private val imageState: MutableState<String>): AsyncTask<String, String, String>() {
class GetURLData

    override fun onPreExecute() {
        super.onPreExecute()
        realState.value = "Подождите..."
    }

    override fun doInBackground(vararg params: String?): String? {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var result: String? = null

        try {
            val url = URL(params[0])
            connection = url.openConnection() as HttpURLConnection?
            connection?.connect()

            val inputStream: InputStream? = connection?.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))

            val buffer = StringBuffer()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                buffer.append(line).append("\n")
            }
            result = buffer.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            Log.e("AsyncTaskError", "MalformedURLException: ${e.message}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("AsyncTaskError", "IOException: ${e.message}")
        } finally {
            if (connection != null) {
                connection.disconnect()
            }
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val jsonObj: JSONObject = JSONObject(result)
        val realTemp = Math.ceil(jsonObj.getJSONObject("main").getDouble("temp") - 274.0).toInt()
        val likeTemp = Math.ceil(jsonObj.getJSONObject("main").getDouble("feels_like") - 274.0).toInt()
        val cloudState = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description").replaceFirstChar{ it.uppercase() }
        val imageCode = jsonObj.getJSONArray("weather").getJSONObject(0).getString("icon")
        realState.value = "$realTemp °C"
        cloudStateArg.value = cloudState
        likeState.value = "Ощущается как: $likeTemp °C"
        imageState.value = imageCode
    }

}
