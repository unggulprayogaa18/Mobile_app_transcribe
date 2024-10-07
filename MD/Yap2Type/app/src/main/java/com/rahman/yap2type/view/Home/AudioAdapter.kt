package com.rahman.yap2type.view.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahman.yap2type.R

class AudioAdapter(private val audioList: List<AudioItem>) : RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_audio, parent, false)
        return AudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audioItem = audioList[position]
        holder.bind(audioItem)
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAudioTitle: TextView = itemView.findViewById(R.id.tvAudioTitle)
        private val tvAudioDate: TextView = itemView.findViewById(R.id.tvAudioDate)

        fun bind(audioItem: AudioItem) {
            tvAudioTitle.text = audioItem.summary
            tvAudioDate.text = audioItem.date
        }
    }
}
