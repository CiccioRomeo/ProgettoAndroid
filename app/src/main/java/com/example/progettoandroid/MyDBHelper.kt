package com.example.progettoandroid

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHelper(context: Context) : SQLiteOpenHelper(context, "Login.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella e inizializzazione del DB
        db.execSQL("CREATE TABLE utenti(user TEXT PRIMARY KEY, password TEXT, nome TEXT, cognome TEXT, sesso TEXT, peso INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Aggiornamento DB quando cambnia versione
    }

    fun insertData( email : String, password: String, nome: String, cognome: String, sesso : String, peso : Int): Boolean {
        val db = this.writableDatabase
        val values: ContentValues = ContentValues();
       values.put("email", email)
       values.put("password", password)
       values.put("nome", nome)
       values.put("cognome", cognome)
       values.put("sesso", sesso)
       values.put("peso", peso)
       val result: Long = db.insert("utenti", null, values)
       if (result.equals(-1))
           return false
       return true
   }
    //Verifica se l'username esiste nella tabella
      fun checkEmail(email: String): Boolean? {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from email where username = ?", arrayOf(email))
        if (cursor.count > 0)
            return true
        return false
    }

    fun checkEmailPassword(email : String, password: String): Boolean? {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery(
            "Select * from users where email = ? and password = ?",
            arrayOf(email, password)
        )
        if (cursor.count > 0)
             return true
        return false
    }

}