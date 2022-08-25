package com.example.mohaniceindustry.power

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mohaniceindustry.R
import com.example.mohaniceindustry.power.ui.main.PowerFragment

class PowerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_power)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PowerFragment.newInstance())
                .commitNow()
        }
    }
}