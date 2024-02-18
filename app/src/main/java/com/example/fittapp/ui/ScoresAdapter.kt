package com.example.fittapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fittapp.R

class ScoresAdapter(private val scoresList: List<Score>) : RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scoresList[position]
        holder.enduranceTextView.text = "Endurance: ${score.endurance}"
        holder.agilityTextView.text = "Agility: ${score.agility}"
        holder.barHangTextView.text = "Bar Hang: ${score.barHang}"
        holder.pullUpsTextView.text = "Pull-Ups: ${score.pullUps}"
    }

    override fun getItemCount() = scoresList.size

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val enduranceTextView: TextView = view.findViewById(R.id.enduranceTextView)
        val agilityTextView: TextView = view.findViewById(R.id.agilityTextView)
        val barHangTextView: TextView = view.findViewById(R.id.hangbarView)
        val pullUpsTextView: TextView = view.findViewById(R.id.pullupsTextView)
    }
}