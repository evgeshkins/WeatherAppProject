package com.example.weatherappproject

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class GetURLData(private val textState: MutableState<String>): AsyncTask<String, String, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
        textState.value = "Подождите..."
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
        val realTemp = Math.ceil(jsonObj.getJSONObject("main").getDouble("temp") - 274.0)
        val likeTemp = Math.ceil(jsonObj.getJSONObject("main").getDouble("feels_like") - 274.0)
        textState.value = "Температура: " + realTemp



    }

}
