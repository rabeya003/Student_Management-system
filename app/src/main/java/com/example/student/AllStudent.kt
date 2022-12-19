package com.example.student

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import kotlin.Function

class AllStudent : AppCompatActivity() {
    companion object {
        val ID = "id"
        val KEY_NAME = "name"
        val KEY_ID = "student_id"
        val KEY_BTC = "batch_no"
        val KEY_EMAIL = "email"
        val KEY_CONTACT = "contact"
        val KEY_ADDRESS = "address"
        var dataList = ArrayList<HashMap<String, String>>()

        lateinit var students_list: ListView
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_student)
        context = this@AllStudent
        students_list = findViewById(R.id.Serial_View)
    }
    override fun onResume() {
        super.onResume()
        val loadTask = LoadJSON()
        loadTask.execute()
    }
    inner class LoadJSON() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            dataList.clear()
        }

        protected override fun doInBackground(vararg args: String): String? {
            val urlParameters = ""
            val xml = com.example.student.Function.excutePost(com.example.student.Function.host + "allStudents.php", urlParameters)
            return xml
        }
        override fun onPostExecute(xml: String?) {
            try {
                Log.d("G", "sTARTING onPost")
                if (xml != null) {
                    val jsonObj =  JSONObject(xml);
                    if(jsonObj.optString("status").toString() == "true"){
                        val jsonData = jsonObj.optString("students").toString();
                        val jsonarr = JSONArray(jsonData)
                        for (i in 0 until jsonarr.length()) {
                            val jsonObject = jsonarr.getJSONObject(i)
                            val map = HashMap<String, String>()
                            map.put(ID, jsonObject.optString(ID).toString())
                            map.put(KEY_NAME, jsonObject.optString(KEY_NAME).toString())
                            map.put(KEY_ID, jsonObject.optString(KEY_ID).toString())
                            map.put(KEY_BTC, jsonObject.optString(KEY_BTC).toString())
                            map.put(KEY_EMAIL, jsonObject.optString(KEY_EMAIL).toString())
                            map.put(KEY_CONTACT, jsonObject.optString(KEY_CONTACT).toString())
                            map.put(KEY_ADDRESS, jsonObject.optString(KEY_ADDRESS).toString())
                            dataList.add(map)
                        }
                        val adapter = CustomAdapter(context, dataList)
                        students_list.setAdapter(adapter)
                        students_list.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                            Toast.makeText(this@AllStudent, dataList.get(+position).get(KEY_ID).toString(), Toast.LENGTH_LONG).show()
                        })
                    }else{
                        Log.e("JSON Error",jsonObj.optString("status").toString());
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