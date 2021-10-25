package com.verindrarizya.movies.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.verindrarizya.movies.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private val binding: ActivitySettingBinding by lazy {
        ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(binding.settingContainer.id, SettingFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}