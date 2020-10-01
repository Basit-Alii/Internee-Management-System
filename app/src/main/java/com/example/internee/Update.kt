package com.example.internee

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.internee.database.DbHelper
import kotlinx.android.synthetic.main.activity_update.*

class Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val intent = getIntent()
        val id = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("Name")
        val fathername = intent.getStringExtra("FatherName")
        val dob = intent.getStringExtra("dob")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("Password")
        etname.setText(name)
        etfathername.setText(fathername)
        etdob.setText(dob)
        etemail.setText(email)
        etpassword.setText(password)

        btnsubmit.setOnClickListener {
            val updatedIntenee = Internee(
                id = id,
                name = etname.text.toString(),
                fatherName = etfathername.text.toString(),
                dob = etdob.text.toString(),
                email = etemail.text.toString(),
                password = etpassword.text.toString()
            )

            val dpHelper = DbHelper(this)
            var result = dpHelper.updateInternee(updatedIntenee)

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
                Toast.makeText(this, "Could not update", Toast.LENGTH_SHORT).show()

            }

        }
    }
}