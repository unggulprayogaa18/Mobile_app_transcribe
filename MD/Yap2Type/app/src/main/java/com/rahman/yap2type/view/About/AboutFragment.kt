package com.rahman.yap2type.view.About

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rahman.yap2type.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val tvAboutUs = view.findViewById<TextView>(R.id.textViewAboutUs)
        val tvContactUs = view.findViewById<TextView>(R.id.textViewContactUs)

        // About Us TextView click listener
        tvAboutUs.setOnClickListener {
            val intent = Intent(requireContext(), AboutUsActivity::class.java)
            startActivity(intent)
        }

        tvContactUs.setOnClickListener {
            val intent = Intent(requireContext(), ContactUsActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    fun openContactUsActivity(view: View) {
        val intent = Intent(requireContext(), ContactUsActivity::class.java)
        startActivity(intent)
    }
}
