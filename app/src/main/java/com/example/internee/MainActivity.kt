package com.example.internee

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.internee.database.DbHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DbHelper(this)
        btnsubmit.setOnClickListener {
            addInterneeToDatabase()
        }
    }

    private fun addInterneeToDatabase() {
        if (etname.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please provide name", Toast.LENGTH_SHORT).show()
            return
        }

        if (etfathername.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please provide father name", Toast.LENGTH_SHORT).show()
            return
        }

        if (etdob.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please provide date of birth", Toast.LENGTH_SHORT).show()
            return
        }

        if (etemail.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please provide email", Toast.LENGTH_SHORT).show()
            return
        }
        if (etpassword.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please provide password", Toast.LENGTH_SHORT).show()
            return
        }

        if (dbHelper.shouldAddInternee(etemail.text.toString())) {
            val result = dbHelper.addInternee(
                name = etname.text.toString(),
                fatherName = etfathername.text.toString(),
                dob = etdob.text.toString(),
                email = etemail.text.toString(),
                password = etpassword.text.toString()
            )

            Toast.makeText(this, "Result is : ${result}", Toast.LENGTH_SHORT).show()

            if (result) {
                var intent = Intent()
                intent.putExtra("result", true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                var intent = Intent()
                intent.putExtra("result", false)
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }

        } else {
            Toast.makeText(this, "You Are already Signed Up", Toast.LENGTH_SHORT).show()
        }


    }
    
}