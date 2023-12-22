package com.example.progettoandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context, "Login.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella e inizializzazione del DB
        db.execSQL("CREATE TABLE utenti(user TEXT PRIMARY KEY, password TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Aggiornamento DB quando cambnia versione
    }

}