package com.example.student

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import kotlin.Function

class AddStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
    }

    override fun onResume() {
        super.onResume()

        val loadTask = LoadJSON()
        val addBtn = findViewById<TextView>(R.id.ad_btn)
        addBtn.setOnClickListener {
            loadTask.execute()
        }
    }
    inner class LoadJSON() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        protected override fun doInBackground(vararg args: String): String? {
            val Id = findViewById<EditText>(R.id.add_id).text.toString()
            val Name = findViewById<EditText>(R.id.add_nme).text.toString()
            val Email = findViewById<EditText>(R.id.add_email).text.toString()
            val Batch = findViewById<EditText>(R.id.add_batch).text.toString()
            val Contact = findViewById<EditText>(R.id.add_contact).text.toString()
            val Address = findViewById<EditText>(R.id.add_address).text.toString()

            val urlParameters = "studentID="+Id+"&name="+Name+"&email="+Email+"&batchNo="+Batch+"&address="+Contact+"&contact="+Address
            val xml = com.example.student.Function.excutePost(com.example.student.Function.host + "addStudent.php", urlParameters)
            return xml
        }

        override fun onPostExecute(xml: String?) {
            try {
                Log.d("G", "sTARTING onPost")

                if (xml != null) {
                    val jsonObj = JSONObject(xml);
                    if (jsonObj.optString("status").toString() == "true") {
                        Toast.makeText(
                            this@AddStudent,
                            jsonObj.optString("msg").toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AddStudent,
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