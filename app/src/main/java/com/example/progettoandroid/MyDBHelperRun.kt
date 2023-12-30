package com.example.progettoandroid

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHelperRun(context: Context) : SQLiteOpenHelper(context, "Run.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella e inizializzazione del DB
        db.execSQL("CREATE TABLE corse(_id INTEGER PRIMARY KEY AUTOINCREMENT,  email TEXT,data TEXT, distanza TEXT, tempo TEXT, calorie TEXT );")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Aggiornamento DB quando cambnia versione
    }
    fun insertData(email : String?, data : String , distanza: String , tempo: String, calorie : String){
        val myDB : SQLiteDatabase  = this.writableDatabase;
        val contentValues=  ContentValues();
        contentValues.put("email", email);
        contentValues.put("distanza", distanza);
        contentValues.put("tempo", tempo);
        contentValues.put("calorie", calorie);
        contentValues.put("data", data);
        myDB.insert("corse", null, contentValues);
    }


    fun getDate(email: String?): MutableList<String> {
        val date: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT data FROM corse WHERE email = ?", arrayOf(email))
        while (cursor.moveToNext()) {
            val elem : String = cursor.getString(0)
            date.add(elem)
        }
        cursor.close()
        db.close()
        return date
    }

    fun getDistanze(email: String?): MutableList<String> {
        val distanze: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT distanza FROM corse WHERE email = ?", arrayOf(email))
        while (cursor.moveToNext()) {
            val elem : String = cursor.getString(0)
            distanze.add(elem)
        }
        cursor.close()
        db.close()
        return distanze
    }


    fun getTempi(email: String?): MutableList<String> {
        val tempi: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT tempo FROM corse WHERE email = ?", arrayOf(email))
        while (cursor.moveToNext()) {
            val elem: String = cursor.getString(0)
            tempi.add(elem)
        }
        cursor.close()
        db.close()
        return tempi
    }

    fun getCalorie(email: String?): MutableList<String> {
        val calorieList: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT calorie FROM corse WHERE email = ?", arrayOf(email))
        while (cursor.moveToNext()) {
            val elem: String = cursor.getString(0)
            calorieList.add(elem)
        }
        cursor.close()
        db.close()
        return calorieList
    }

}


