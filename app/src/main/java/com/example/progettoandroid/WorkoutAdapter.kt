package com.example.progettoandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

data class WorkoutEntry(val date: String, val km: String, val time: String, val calories: String)

class WorkoutAdapter(private val entries: MutableList<WorkoutEntry>) :
    RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val kmTextView: TextView = itemView.findViewById(R.id.kmTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_statistiche, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.dateTextView.text = entry.date
        holder.kmTextView.text = entry.km
        holder.timeTextView.text = entry.time
        holder.caloriesTextView.text = entry.calories
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    fun addEntry(entry: WorkoutEntry) {
        entries.add(entry)
        notifyDataSetChanged()
    }

    fun removeEntry(position: Int) {
        if (position in 0 until entries.size) {
            entries.removeAt(position)
            notifyDataSetChanged()
        }
    }
}