package com.rahman.yap2type.view.Home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.rahman.yap2type.R
import java.util.Locale

class RecordActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var speechRecognizer: SpeechRecognizer

    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        startButton = findViewById(R.id.start_button)
        saveButton = findViewById(R.id.save_button)
        deleteButton = findViewById(R.id.delete_button)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textvw)
        progressBar = findViewById(R.id.progress_bar)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )

        val recognitionListener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                progressBar.visibility = View.VISIBLE
                imageView.visibility = View.VISIBLE
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                imageView.visibility = View.GONE
            }

            override fun onError(error: Int) {
                progressBar.visibility = View.GONE
                imageView.visibility = View.GONE
            }

            override fun onResults(results: Bundle?) {
                progressBar.visibility = View.GONE
                val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                result?.let {
                    val recognizedText = it[0]
                    textView.text = recognizedText

                    saveButton.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer.setRecognitionListener(recognitionListener)

        startButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    REQUEST_RECORD_AUDIO_PERMISSION
                )
                return@setOnClickListener
            }

            if (isRecording) {
                speechRecognizer.stopListening()
                isRecording = false
                startButton.text = "Start"
                imageView.visibility = View.GONE
            } else {
                // Memulai rekaman
                speechRecognizer.startListening(speechRecognizerIntent)
                isRecording = true
                startButton.text = "Stop"
                imageView.visibility = View.VISIBLE
            }
        }

        saveButton.setOnClickListener {
            val recognizedText = textView.text.toString()
            val intent = Intent(this, VoiceToTextActivity::class.java)
            intent.putExtra("recognizedText", recognizedText)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            textView.text = ""
            saveButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
            startButton.text = "Start"
            isRecording = false
            imageView.visibility = View.GONE
        }
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}
