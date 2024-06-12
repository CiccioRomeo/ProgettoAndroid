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

    fun getPeso(email : String?): Int {
        val myDB: SQLiteDatabase = this.writableDatabase;
        val cursor: Cursor =
            myDB.rawQuery("Select peso from utenti where email = ? ", arrayOf(email));
        if (cursor.moveToFirst()) {
            val peso: Int = cursor.getInt(0)
            cursor.close()
            return peso
        } else {
            cursor.close()
            return -1
        }
    }
    fun getNome(email: String?): String {
        val myDB: SQLiteDatabase = this.writableDatabase;
        val cursor: Cursor =
            myDB.rawQuery("Select nome from utenti where email = ? ", arrayOf(email));
        if (cursor.moveToFirst()) {
            val nome: String = cursor.getString(0)
            cursor.close()
            return nome
        } else {
            cursor.close()
            return " "
        }
    }
    fun getCognome(email : String?): String{
        val myDB: SQLiteDatabase = this.writableDatabase;
        val cursor: Cursor =
            myDB.rawQuery("Select cognome from utenti where email = ? ", arrayOf(email));
        if (cursor.moveToFirst()) {
            val cognome: String = cursor.getString(0)
            cursor.close()
            return cognome
        } else {
            cursor.close()
            return " "
        }
    }

    fun setNome(email: String?, nuovoNome :String){
        val myDB: SQLiteDatabase = this.writableDatabase;
        myDB.execSQL("Update utenti set nome = ?  where email = ? ", arrayOf(nuovoNome, email));

    }
    fun setCognome(email: String?, nuovoCognome :String){
        val myDB: SQLiteDatabase = this.writableDatabase;
        myDB.execSQL("Update utenti set cognome = ?  where email = ? ", arrayOf(nuovoCognome , email));
    }

    fun setPeso(email: String?, nuovoPeso :Int){
        val myDB: SQLiteDatabase = this.writableDatabase;
        myDB.execSQL("Update utenti set peso = ?  where email = ? ", arrayOf("$nuovoPeso" , email));
    }


}
