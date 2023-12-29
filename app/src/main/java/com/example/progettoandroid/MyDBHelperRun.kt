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

    @SuppressLint("Range")
    fun getRunsByEmail(email: String?): List<Run> {
        val runs = mutableListOf<Run>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM corse WHERE email = ?", arrayOf(email))
        while (cursor.moveToNext()) {
            val run = Run(
                cursor.getString(cursor.getColumnIndex("data")),
                cursor.getString(cursor.getColumnIndex("distanza")),
                cursor.getString(cursor.getColumnIndex("tempo")),
                cursor.getString(cursor.getColumnIndex("calorie"))
            )
            runs.add(run)
        }
        cursor.close()
        db.close()
        return runs
    }

    // Metodo che restituisce una lista di liste con i risultati della query precedente
    fun getRunsListByEmail(email: String?): List<List<String>> {
        val runs = getRunsByEmail(email)
        return runs.map {
            listOf( it.data, it.distanza, it.tempo, it.calorie)
        }
    }
}

data class Run(val data: String, val distanza: String, val tempo: String, val calorie: String)
