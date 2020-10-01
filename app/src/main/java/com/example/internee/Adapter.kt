package com.example.internee

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.internee.interfaces.OnItemClick

class MyListAdapter(
    private val context: Activity,
    private val data: ArrayList<Internee>
) : RecyclerView.Adapter<MyListAdapter.RecycleViewHolder>() {

    private lateinit var onItemClick: OnItemClick

/*    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.recyclerview, null, true)

        val nameText = rowView.findViewById(R.id.tvname) as TextView
        val fathernameText = rowView.findViewById(R.id.tvfathername) as TextView
        val dobText = rowView.findViewById(R.id.tvdob) as TextView
        val emailText = rowView.findViewById(R.id.tvemail) as TextView
        val passwrdText = rowView.findViewById(R.id.tvemail) as TextView
        nameText.text = name[position]
        fathernameText.text = fathername[position]
        dobText.text = dob[position]
        emailText.text = email[position]
        passwrdText.text=password[position]

        return rowView
    }*/

    class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText = itemView.findViewById<TextView>(R.id.tvname)
        var fatherNameText = itemView.findViewById<TextView>(R.id.tvfathername)
        var dobText = itemView.findViewById<TextView>(R.id.tvdob)
        var emailText = itemView.findViewById<TextView>(R.id.tvemail)
        var passwordText = itemView.findViewById<TextView>(R.id.tvpassword)
        var updateButton = itemView.findViewById<Button>(R.id.btnupdate)
        var deletebutton = itemView.findViewById<Button>(R.id.btndelete)
    }

    fun setItemTap(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview, parent, false)
        val viewHolder = RecycleViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val internee = data[position]
        holder.nameText.setText(internee.name)
        holder.fatherNameText.setText(internee.fatherName)
        holder.dobText.setText(internee.dob)
        holder.emailText.setText(internee.email)
        holder.passwordText.setText(internee.password)

        holder.updateButton.setOnClickListener {
            this.onItemClick.update(it, position)
        }
        holder.deletebutton.setOnClickListener {
            this.onItemClick.delete(it, position)
        }
    }
}