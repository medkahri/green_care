package com.enetcom.aquaponic

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.enetcom.aquaponic.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database

import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
     lateinit var database : DatabaseReference
     val DataBase = Firebase.database
     val Myref = DataBase.getReference("house1")








   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
       binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)




       binding.readDataBtn.setOnClickListener {

           readData("home1")

       }
       var getdata = object : ValueEventListener{
           override fun onCancelled(p0 : DatabaseError){}

           override fun onDataChange(p0: DataSnapshot) {
               var tp = StringBuilder()
               var hm = StringBuilder()
               for (i in p0.children){
                   var temp = i.child("temp").getValue()
                   var hum = i.child("hum").getValue()
                   tp.append("$temp")
                   hm.append("$hum")




               }
               binding.humText.setText(hm)
               binding.tempText.setText(tp)


           }




   }
       database = FirebaseDatabase.getInstance().getReference()
       database.addValueEventListener(getdata)
       database.addListenerForSingleValueEvent(getdata)


   }


       fun readData(houseName: String) {

            database = FirebaseDatabase.getInstance().getReference()
            database.child(houseName).get().addOnSuccessListener {

                if (it.exists()) {

                    var temper = it.child("temp").value
                    var humid = it.child("hum").value

                    Toast.makeText(this, "Successfuly Read", Toast.LENGTH_SHORT).show()

                    binding.tempText.text = temper.toString()
                    binding.humText.text = humid.toString()


                }else{

                    Toast.makeText(this,"Error retreiving data",Toast.LENGTH_SHORT).show()


                }

            }.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


            }
    }





}
