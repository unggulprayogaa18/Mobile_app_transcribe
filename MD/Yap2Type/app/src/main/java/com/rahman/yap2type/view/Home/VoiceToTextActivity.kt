package com.rahman.yap2type.view.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.rahman.yap2type.R
import java.text.SimpleDateFormat
import java.util.*

class VoiceToTextActivity : AppCompatActivity() {

    private lateinit var dateText: TextView
    private lateinit var summaryText: TextView
    private lateinit var summarizeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_to_text)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        dateText = findViewById(R.id.date_text)
        summaryText = findViewById(R.id.summary_text)
        summarizeButton = findViewById(R.id.summarizeButton)

        val recognizedText = intent.getStringExtra("recognizedText")
        recognizedText?.let {
            summaryText.text = it
        }

        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDateTime)
        dateText.text = formattedDate

        summarizeButton.setOnClickListener {
            val summary = summaryText.text.toString()
            val bottomSheet = SummaryActivity.newInstance(summary)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}
