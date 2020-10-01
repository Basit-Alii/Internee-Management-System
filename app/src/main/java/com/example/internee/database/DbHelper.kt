package com.example.internee.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.internee.Internee
import com.example.internee.utils.Constants


class DbHelper(context: Context) : SQLiteOpenHelper(context, Constants.databaseName, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table ${Constants.databaseName} " +
                    "(id integer primary key, ${Constants.INTERNEES_COLUMN_NAME} text,${Constants.INTERNEES_COLUMN_FATHER_NAME} text,${Constants.INTERNEES_COLUMN_DOB} text, ${Constants.INTERNEES_COLUMN_EMAIL} text,${Constants.INTERNEES_COLUMN_PASSWORD} text)"
        )
    }

    override fun onUpgrade(
        database: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

    }

    fun addInternee(
        name: String?,
        fatherName: String?,
        email: String?,
        dob: String?,
        password: String?
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.INTERNEES_COLUMN_NAME, name)
        contentValues.put(Constants.INTERNEES_COLUMN_FATHER_NAME, fatherName)
        contentValues.put(Constants.INTERNEES_COLUMN_EMAIL, email)
        contentValues.put(Constants.INTERNEES_COLUMN_DOB, dob)
        contentValues.put(Constants.INTERNEES_COLUMN_PASSWORD, password)
        db.insert(Constants.databaseName, null, contentValues)
        return true
    }

    fun getAllInternees(): ArrayList<Internee> {
        val internees = ArrayList<Internee>()

        //hp = new HashMap();
        val db = this.readableDatabase
        val res: Cursor = db.rawQuery("select * from ${Constants.databaseName}", null)
        res.moveToFirst()
        while (res.isAfterLast() === false) {
            internees.add(
                Internee(
                    id = res.getInt(res.getColumnIndex("id")),
                    name = res.getString(
                        res.getColumnIndex(Constants.INTERNEES_COLUMN_NAME)
                    ),
                    fatherName = res.getString(
                        res.getColumnIndex(Constants.INTERNEES_COLUMN_FATHER_NAME)
                    ),
                    dob = res.getString(res.getColumnIndex(Constants.INTERNEES_COLUMN_DOB)),
                    email = res.getString(res.getColumnIndex(Constants.INTERNEES_COLUMN_EMAIL)),
                    password = res.getString(res.getColumnIndex(Constants.INTERNEES_COLUMN_PASSWORD))
                )
            )
            res.moveToNext()
        }
        return internees
    }

    fun shouldAddInternee(email: String): Boolean {
        val db = this.readableDatabase
        var shouldAdded = true
        val res: Cursor = db.rawQuery("select * from ${Constants.databaseName}", null)
        res.moveToFirst()
        while (res.isAfterLast() === false) {
            if (email == res.getString(res.getColumnIndex(Constants.INTERNEES_COLUMN_EMAIL))) {
                shouldAdded = false
            }
            res.moveToNext()
        }

        return shouldAdded
    }

    fun shouldDeleteInternee(receivedId: Int): Boolean {
        val db = this.readableDatabase
        db.execSQL("delete from ${Constants.databaseName} where id = ${receivedId}")
        return true
    }

    fun updateInternee(data: Internee): Boolean {
        val db = this.writableDatabase
        db.execSQL("UPDATE ${Constants.databaseName} set ${Constants.INTERNEES_COLUMN_NAME} = '${data.name}', ${Constants.INTERNEES_COLUMN_FATHER_NAME} = '${data.fatherName}', ${Constants.INTERNEES_COLUMN_DOB}='${data.dob}',${Constants.INTERNEES_COLUMN_EMAIL}='${data.email}',${Constants.INTERNEES_COLUMN_PASSWORD}='${data.password}' where id = ${data.id}")
        return true
    }
}