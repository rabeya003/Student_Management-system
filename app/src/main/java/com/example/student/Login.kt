package com.example.student

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import kotlin.Function

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gotoRegBtn = findViewById<TextView>(R.id.create_btn)
        gotoRegBtn.setOnClickListener {
            intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        // put your code here...
        val loadTask = LoadJSON()
        val loginBtn = findViewById<TextView>(R.id.login_btn)
        loginBtn.setOnClickListener {
            loadTask.execute()
        }
    }

    inner class LoadJSON : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg args: String): String? {
            val loginEmail = findViewById<EditText>(R.id.log_email).text.toString()
            val loginPass = findViewById<EditText>(R.id.log_password).text.toString()

            val urlParameters = "email="+loginEmail+"&password="+loginPass
            val xml = com.example.student.Function.excutePost(com.example.student.Function.host + "login.php", urlParameters)
            return xml
        }

        override fun onPostExecute(xml: String?) {
            try {
                Log.d("G", "sTARTING onPost")
                if (xml != null) {
                    val jsonObj = JSONObject(xml)
                    if (jsonObj.optString("status").toString() == "true") {
                        intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@Login,
                            jsonObj.optString("msg").toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.d("Q", "XML is null")
                }

            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }
        }
    }

}