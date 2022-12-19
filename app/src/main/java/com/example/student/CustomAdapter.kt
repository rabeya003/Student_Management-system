package com.example.student

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter (private val context: Context,
                     private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {
    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]

        val rowView = inflater.inflate(R.layout.activity_single_student, parent, false)
        rowView.findViewById<TextView>(R.id.view_name).text = dataitem[AllStudent.KEY_NAME]
        rowView.findViewById<TextView>(R.id.view_id).text = dataitem[AllStudent.KEY_ID]
        rowView.findViewById<TextView>(R.id.view_batch).text = dataitem[AllStudent.KEY_BTC]
        rowView.findViewById<TextView>(R.id.view_email).text = dataitem[AllStudent.KEY_EMAIL]
        rowView.findViewById<TextView>(R.id.view_contact).text = dataitem[AllStudent.KEY_CONTACT]
        rowView.findViewById<TextView>(R.id.view_address).text = dataitem[AllStudent.KEY_ADDRESS]

        rowView.tag = position
        return rowView
    }
}