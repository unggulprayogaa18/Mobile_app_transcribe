package com.rahman.yap2type.view.About

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahman.yap2type.R

class ContactUsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        supportActionBar?.apply {
            title = "Contact Us"
            setDisplayHomeAsUpEnabled(true)
        }

        recyclerView = findViewById(R.id.recyclerViewMembers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        memberAdapter = MemberAdapter(getMembers())
        recyclerView.adapter = memberAdapter

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getMembers(): List<Member> {
        return listOf(
            Member("Muhammad Irfan", "m132d4ky1867@bangkit.academy", R.drawable.irfan),
            Member("Farig Muhammad Taqy", "m132d4ky2015@bangkit.academy", R.drawable.farig),
            Member("Akhmad Fadilla Akbar", "m119d4ky1765@bangkit.academy", R.drawable.akbar),
            Member("Nabilla Zhavirah", "a573d4kx4374@bangkit.academy", R.drawable.nabila),
            Member("Rahman Faisal", "a132d4ky4559@bangkit.academy", R.drawable.rahman),
            Member("Puput Unggul Prayoga", "c152d4ky0148@bangkit.academy", R.drawable.yoga),
            Member("Arifin Mulqa Maulana", "c152d4ky0991@bangkit.academy", R.drawable.arifin)
        )
    }
}
