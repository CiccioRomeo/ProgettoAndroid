package com.example.progettoandroid

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.location.Location


class MyDBHelperRun(context: Context) : SQLiteOpenHelper(context, "Run.db",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella e inizializzazione del DB
        db.execSQL("CREATE TABLE corse(_id INTEGER PRIMARY KEY AUTOINCREMENT,  email TEXT,data TEXT, distanza TEXT, tempo TEXT, calorie TEXT , currentLat DOUBLE , currentLng DOUBLE);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Aggiornamento DB quando cambnia versione
    }
    fun insertData(email : String?, data : String , distanza: String , tempo: String, calorie : String, currentLat : Double, currentLng : Double){
        val myDB : SQLiteDatabase  = this.writableDatabase;
        val contentValues=  ContentValues();
        contentValues.put("email", email);
        contentValues.put("distanza", distanza);
        contentValues.put("tempo", tempo);
        contentValues.put("calorie", calorie);
        contentValues.put("data", data);
        contentValues.put("currentLat", currentLat);
        contentValues.put("currentLng", currentLng);
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


    fun ultimaCorsaVicina(email: String?, latitude : Double, longitude : Double, context : Context ){
        val currentLocation : Location = Location("this")
        val location : Location = Location("this")
        currentLocation.latitude = latitude
        currentLocation.longitude= longitude
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM corse WHERE email = ?", arrayOf(email))
        val latIndex = cursor.getColumnIndex("currentLat")
        val lngIndex = cursor.getColumnIndex("currentLng")
        while (cursor.moveToNext()) {
            val lat: Double = cursor.getDouble(latIndex)
            val lng: Double = cursor.getDouble(lngIndex)
            location.latitude = lat
            location.longitude = lng
            val distance: Double = location.distanceTo(currentLocation) / 1000.00
            if(distance <= 2.0){
                val dataIndex = cursor.getColumnIndex("data")
                val distanzaIndex = cursor.getColumnIndex("distanza")
                val tempoIndex = cursor.getColumnIndex("tempo")
                val calorieIndex = cursor.getColumnIndex("calorie")
                val data : String = cursor.getString(dataIndex)
                val distanza : String = cursor.getString(distanzaIndex)
                val tempo : String = cursor.getString(tempoIndex)
                val calorie : String = cursor.getString(calorieIndex)
                val mAlertDialog = AlertDialog.Builder(context)
                mAlertDialog.setTitle("Ehi hai gia corso da queste parti!")
                mAlertDialog.setMessage("Ecco le statistiche dell'ultima corsa che hai fatto nelle vicinanze \n \nData: $data \n \nDistanza: $distanza\n\nTempo: $tempo\n\nCalorie: $calorie")
                mAlertDialog.setNegativeButton("Ok") { dialog, id ->

                }
                mAlertDialog.show()
            }

        }
        cursor.close()
        db.close()

    }


}


