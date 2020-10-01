package com.example.internee

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.internee.database.DbHelper
import com.example.internee.interfaces.OnItemClick
import kotlinx.android.synthetic.main.activity_display_internee.*
import kotlinx.android.synthetic.main.recyclerview.*
import java.text.FieldPosition


class DisplayInternee : AppCompatActivity() {
    lateinit var dbHelper: DbHelper
    lateinit var adapter: MyListAdapter
    var internees = arrayListOf<Internee>()
    //lateinit var childView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_internee)
        dbHelper = DbHelper(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
/*        recyclerview.addOnItemTouchListener(object : OnItemTouchListener {
            var gestureDetector =
                GestureDetector(this@DisplayInternee, object : SimpleOnGestureListener() {
                    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
                        return true
                    }
                })

            override fun onInterceptTouchEvent(
                Recyclerview: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                childView = Recyclerview.findChildViewUnder(motionEvent.x, motionEvent.y)!!
                var position = 0;

                if (childView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    position = Recyclerview.getChildAdapterPosition(childView)

                    update(internees[position])

*//*
     val result = dbHelper.shouldDeleteInternee(internees[position].id)
                    if (result) {
                        Toast.makeText(this@DisplayInternee, "Deleted", Toast.LENGTH_SHORT).show()
                        refreshData()
                    } else {
                        Toast.makeText(
                            this@DisplayInternee,
                            "Sorry couldn't delete Internee",
                            Toast.LENGTH_SHORT
                        ).show()

                    }*//*
                }
                return false
            }

            override fun onTouchEvent(
                Recyclerview: RecyclerView,
                motionEvent: MotionEvent
            ) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })*/
        adapter = MyListAdapter(this, internees)
        adapter.setItemTap(object: OnItemClick{
                override fun update(view: View,position: Int){
                    val intent=Intent(this@DisplayInternee, Update::class.java)
                    intent.putExtra("id", internees[position].id)
                    intent.putExtra("Name", internees[position].name)
                    intent.putExtra("FatherName", internees[position].fatherName)
                    intent.putExtra("dob", internees[position].dob)
                    intent.putExtra("email", internees[position].email)
                    intent.putExtra("Password", internees[position].password)
                    startActivityForResult(intent, 102)
                }

            override fun delete(view: View, position: Int) {
                val alert=AlertDialog.Builder(this@DisplayInternee)
                alert.setTitle("Deleted")
                alert.setIcon(android.R.drawable.ic_dialog_alert)
                alert.setMessage("Do you want to Delete")
                alert.setPositiveButton("Yes") { dialoginterface, i ->
                    Toast.makeText(this@DisplayInternee,"Deleted",Toast.LENGTH_LONG).show()
                    dialoginterface.cancel()
                    val result=dbHelper.shouldDeleteInternee(internees[position].id)
                    if (result) {
                        Toast.makeText(this@DisplayInternee, "Deleted", Toast.LENGTH_SHORT).show()
                        refreshData()
                    }
               else {
                    Toast.makeText(
                        this@DisplayInternee,
                        "Sorry couldn't delete Internee",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                }
                alert.setNegativeButton("Cancel"){dialoginterface,i  ->
                    Toast.makeText(this@DisplayInternee, "Canceled", Toast.LENGTH_SHORT).show()
                    dialoginterface.cancel()
                }
                alert.create()
                alert.show()
            }
        })
        recyclerview.adapter = adapter
        refreshData()

    }

    fun add(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, 101)
    }

    fun refreshData() {
        internees.clear()
        internees.addAll(dbHelper.getAllInternees())
        adapter.notifyDataSetChanged()
    }

   /* fun update(internee: Internee) {
        val intent = Intent(this, Update::class.java)
        intent.putExtra("id", internee.id)
        intent.putExtra("Name", internee.name)
        intent.putExtra("FatherName", internee.fatherName)
        intent.putExtra("dob", internee.dob)
        intent.putExtra("email", internee.email)
        intent.putExtra("Password", internee.password)
        startActivity(intent)
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    if (data?.getBooleanExtra("result", false) == true) {
                        refreshData()
                        Toast.makeText(this, "Internee is added", Toast.LENGTH_SHORT).show()
                    }
                }

                102 -> {
                    if (data?.getBooleanExtra("result", false) == true) {
                        refreshData()
                        Toast.makeText(this, "Internee is Updated", Toast.LENGTH_SHORT).show()
                    }
                }

                else -> {
                    Toast.makeText(this, "Internee could not be Updated", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Internee could not be Added", Toast.LENGTH_SHORT).show()
        }
    }
}






