package com.rahman.yap2type.view.Home

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahman.yap2type.R

class HomeFragment : Fragment() {

    private lateinit var adapter: AudioAdapter
    private lateinit var audioList: MutableList<AudioItem>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnAddAudio: Button = view.findViewById(R.id.btnAddAudio)
        audioList = mutableListOf()
        adapter = AudioAdapter(audioList)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", AppCompatActivity.MODE_PRIVATE)

        btnAddAudio.setOnClickListener {
            val intent = Intent(activity, RecordActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        loadAudioData()
    }

    private fun loadAudioData() {
        audioList.clear()
        val count = sharedPreferences.getInt("count", 0)

        for (i in 0 until count) {
            val summary = sharedPreferences.getString("summary_$i", "") ?: ""
            val date = sharedPreferences.getString("date_$i", "") ?: ""

            if (summary.isNotEmpty() && date.isNotEmpty()) {
                audioList.add(AudioItem(summary, date))
            }
        }

        adapter.notifyDataSetChanged()
    }
}
