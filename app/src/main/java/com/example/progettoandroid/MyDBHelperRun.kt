package com.example.progettoandroid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHelperRun(context: Context) : SQLiteOpenHelper(context, "Run.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella e inizializzazione del DB
        db.execSQL("CREATE TABLE corse(_id INTEGER PRIMARY KEY AUTOINCREMENT, distanza DOUBLE, tempo TEXT, calorie INTEGER );")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Aggiornamento DB quando cambnia versione
    }
    fun insertData(distanza: Double , tempo: String, calorie : Int){
        val myDB : SQLiteDatabase  = this.writableDatabase;
        val contentValues=  ContentValues();
        contentValues.put("distanza", distanza);
        contentValues.put("tempo", tempo);
        contentValues.put("calorie", calorie);
        myDB.insert("corse", null, contentValues);
    }
}