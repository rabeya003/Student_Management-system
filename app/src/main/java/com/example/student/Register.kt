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

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val gotoLoginBtn = findViewById<TextView>(R.id.log_here_btn)
        gotoLoginBtn.setOnClickListener {
            intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        val loadTask = LoadJSON()
        val regBtn = findViewById<TextView>(R.id.reg_btn)
        regBtn.setOnClickListener {
            loadTask.execute()
        }
    }

    inner class LoadJSON : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg args: String): String? {
            val regname = findViewById<EditText>(R.id.reg_name).text.toString()
            val regemail = findViewById<EditText>(R.id.reg_email).text.toString()
            val regpass = findViewById<EditText>(R.id.reg_password).text.toString()
            val regcontact = findViewById<EditText>(R.id.reg_contact).text.toString()
            val regaddress = findViewById<EditText>(R.id.reg_address).text.toString()

            val urlParameters = "name="+regname+"&email="+regemail+"&password="+regpass+"&contact="+regcontact+"&address="+regaddress
            val xml = com.example.student.Function.excutePost(com.example.student.Function.host + "registration.php", urlParameters)
            return xml
        }

        override fun onPostExecute(xml: String?) {
            try {
                Log.d("G", "sTARTING onPost")

                if (xml != null) {
                    val jsonObj = JSONObject(xml)
                    if (jsonObj.optString("status").toString() == "true") {
                        intent = Intent(this@Register, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@Register,
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