package com.example.student

import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class Function {
    companion object {
        var host = "https://imraju.com/labfinal/api/"


        fun excutePost(targetURL: String, urlParameters: String?): String? {
            //trustSSL();
            var targetURL = targetURL
            try {
                targetURL = targetURL.replace(" ".toRegex(), "%20")
            } catch (e: Exception) {
            }
            val url: URL
            var connection: HttpURLConnection? = null
            return try {
                //Create connection
                url = URL(targetURL)
                connection = url.openConnection() as HttpURLConnection
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.setUseCaches(false)
                connection.setDoInput(true)
                connection.setDoOutput(true)

                //Send request
                val bw = BufferedWriter(OutputStreamWriter(connection.getOutputStream(), "UTF-8"))
                bw.write(urlParameters)
                bw.flush()
                bw.close()

                //Get Response
                val `is`: InputStream = connection.inputStream
                val rd = BufferedReader(InputStreamReader(`is`))
                var line: String?
                val response = StringBuffer()
                while (rd.readLine().also { line = it } != null) {
                    response.append(line)
                    response.append('\r')
                }
                rd.close()
                //return decompress(response.toString());
                return response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                if (connection != null) {
                    connection.disconnect()
                }
            }
        }


        fun excuteGet(targetURL: String?, urlParameters: String?): String? {
            val url: URL
            var connection: HttpURLConnection? = null
            return try {
                //Create connection
                url = URL(targetURL)
                connection = url.openConnection() as HttpURLConnection
                //connection.setRequestMethod("POST");
                //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("content-type", "application/json;  charset=utf-8")
                connection.setRequestProperty("Content-Language", "en-US")
                connection.setUseCaches(false)
                connection.setDoInput(true)
                connection.setDoOutput(false)
                val `is`: InputStream
                val status: Int = connection.getResponseCode()
                `is` =
                    if (status != HttpURLConnection.HTTP_OK) connection.getErrorStream() else connection.inputStream
                val rd = BufferedReader(InputStreamReader(`is`))
                var line: String?
                val response = StringBuffer()
                while (rd.readLine().also { line = it } != null) {
                    response.append(line)
                    response.append('\r')
                }
                rd.close()
                response.toString()
            } catch (e: Exception) {
                null
            } finally {
                if (connection != null) {
                    connection.disconnect()
                }
            }
        }
    }
}