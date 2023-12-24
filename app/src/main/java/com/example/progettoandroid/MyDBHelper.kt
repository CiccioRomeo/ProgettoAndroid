package com.example.progettoandroid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHelper(context: Context) : SQLiteOpenHelper(context, "Login.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella e inizializzazione del DB
        db.execSQL("CREATE TABLE utenti(email TEXT PRIMARY KEY, password TEXT, nome TEXT, cognome TEXT, peso  INTEGER );")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Aggiornamento DB quando cambnia versione
    }
   fun insertData(email: String , password: String, nome: String, cognome: String, peso : Int): Boolean{
        val myDB : SQLiteDatabase  = this.writableDatabase;
        val contentValues=  ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("nome", nome);
        contentValues.put("cognome", cognome);
        //contentValues.put("sesso", sesso);
        contentValues.put("peso", peso);
        val result : Long = myDB.insert("utenti", null, contentValues);
        if(result.equals(-1))
            return false;
       return true;
    }

    fun checkEmail(email: String) : Boolean {
        val myDB : SQLiteDatabase  = this.writableDatabase;
        val cursor: Cursor = myDB.rawQuery("Select * from utenti where email = ?", arrayOf(email));
        if (cursor.count > 0)
                return true
        return false
    }

    fun checkEmailPassword(email: String,  password: String): Boolean{
        val myDB : SQLiteDatabase= this.writableDatabase;
        val cursor: Cursor = myDB.rawQuery("Select * from utenti where email = ? and password = ?", arrayOf(email,password));
        if(cursor.count >0)
            return true;
        return false;
    }
}
