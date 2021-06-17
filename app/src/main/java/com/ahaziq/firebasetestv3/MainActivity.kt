package com.ahaziq.firebasetestv3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase
import com.ahaziq.firebasetestv3.R.id
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedIntanceState: Bundle?) {
        super.onCreate(savedIntanceState)
        setContentView(R.layout.activity_main)
        var database = FirebaseDatabase.getInstance().reference
        database.setValue("Attendance Database")


        btnInsert.setOnClickListener{

            var matric_no = etMatricNumber.text.toString().toInt()
            var body_temp = etBodyTemp.text.toString()

            database.child(matric_no.toString()).setValue(Attendees(body_temp))
        }

        var getdata = object : ValueEventListener {


            override fun onDataChange(p0: DataSnapshot) {

                var sb = StringBuilder()
                for(i in p0.children){
                //    var matricno =  i.child("matric_no").getValue()
                    var bodytemp = i.child("body_temp").getValue()

                    sb.append("|    ${i.key}    |   $bodytemp°C    \n")
                }

                tvDatabase.setText(sb)
            }

            override fun onCancelled(p0: DatabaseError) {}
        }

        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }

}