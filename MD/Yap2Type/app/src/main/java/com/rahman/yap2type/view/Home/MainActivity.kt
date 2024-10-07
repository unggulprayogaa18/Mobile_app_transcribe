package com.rahman.yap2type.view.Home

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rahman.yap2type.R
import com.rahman.yap2type.view.About.AboutFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fab: FloatingActionButton
    private lateinit var parentLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)
        parentLayout = findViewById(R.id.fragment_container)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.settings -> AboutFragment()
                else -> throw IllegalArgumentException("Unexpected item ID")
            }
            replaceFragment(selectedFragment)
            true
        }

        fab.setOnClickListener {
            showPopupWindow()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun showPopupWindow() {
        val inflater = LayoutInflater.from(this)
        val popupView = inflater.inflate(R.layout.popup_source, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popupWindow.elevation = 10.0f
        popupWindow.isFocusable = true
        popupWindow.update()

        val dimBackground = ColorDrawable(resources.getColor(android.R.color.black, null))
        dimBackground.alpha = 0
        parentLayout.foreground = dimBackground
        animateDim(parentLayout, 150, 128)

        popupWindow.setOnDismissListener {
            animateDim(parentLayout, 150, 0)
        }

        popupWindow.showAtLocation(parentLayout, android.view.Gravity.CENTER, 0, 0)

        popupView.findViewById<View>(R.id.tvVideoRecorder).setOnClickListener {
            popupWindow.dismiss()
            navigateToVideoRecorder()
        }
    }

    private fun animateDim(view: View, duration: Long, alpha: Int) {
        val animator = ObjectAnimator.ofInt(view.foreground, "alpha", alpha)
        animator.duration = duration
        animator.start()
    }

    private fun navigateToVideoRecorder() {
        val intent = Intent(this, RecordActivity::class.java)
        startActivity(intent)
    }
}
