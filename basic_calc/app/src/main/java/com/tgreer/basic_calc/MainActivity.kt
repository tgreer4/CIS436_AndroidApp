package com.tgreer.basic_calc

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), calc_bttns.calc_bttnsListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onButtonClick(value: String) {
        val displayFragment = supportFragmentManager.findFragmentById(R.id.calc_display) as display_bar
        displayFragment.setString(value)
    }
}