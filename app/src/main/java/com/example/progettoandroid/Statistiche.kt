package com.example.progettoandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

class Statistiche : Fragment() {

    private lateinit var dbRun : MyDBHelperRun
    private lateinit var corse : List<List<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var email = arguments?.getString("email")
        dbRun = MyDBHelperRun(requireContext())
        if (email == null) {
            Toast.makeText(requireContext(), "email nulla", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "email presente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_statistiche, container, false)
        val tableLayout = rootView.findViewById<TableLayout>(R.id.tableLayout)

        //val email = arguments?.getString("email")

        //Toast.makeText(requireContext(),email,Toast.LENGTH_SHORT).show()
/*
        dbRun = MyDBHelperRun(requireContext())


        corse = dbRun.getRunsListByEmail(email)



       //Esempio di dati da inserire nella tabella (puoi sostituire con i tuoi dati dinamici)
        val data = getArrayOf(0,corse)
        val kmPercorsi = getArrayOf(1,corse)
        val tempo = getArrayOf(2,corse)
        val calorieBruciate = getArrayOf(3,corse)

        // Creazione dinamica della tabella
        for (i in data.indices) {
            val row = TableRow(activity)
            val params = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            row.layoutParams = params

            val dataTextView = TextView(activity)
            dataTextView.text = data[i]

            val kmPercorsiTextView = TextView(activity)
            kmPercorsiTextView.text = kmPercorsi[i]

            val tempoTextView = TextView(activity)
            tempoTextView.text = tempo[i]

            val calorieBruciateTextView = TextView(activity)
            calorieBruciateTextView.text = calorieBruciate[i]

            row.addView(dataTextView)
            row.addView(kmPercorsiTextView)
            row.addView(tempoTextView)
            row.addView(calorieBruciateTextView)

            tableLayout.addView(row)
        }
*/

        return rootView
    }

    private fun getArrayOf(i : Int, corse : List<List<String>>) : MutableList<String>{
        val  res :  MutableList<String> = arrayListOf()
        for(corsa in corse){
            res.add(corsa[i])
            Log.d("Dio",corsa[i])
        }
        return res
    }
}