package com.rahman.yap2type.view.About

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.rahman.yap2type.R

class AboutUsActivity : AppCompatActivity() {

    private lateinit var textViewAboutUsContent: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        supportActionBar?.apply {
            title = "About Us"
            setDisplayHomeAsUpEnabled(true)
        }

        textViewAboutUsContent = findViewById(R.id.textViewAboutUsContent)
        textViewAboutUsContent.text = getString(R.string.about_yap2type_content)

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
