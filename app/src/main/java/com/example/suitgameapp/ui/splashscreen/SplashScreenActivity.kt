package com.example.suitgameapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.suitgameapp.R
import com.example.suitgameapp.ui.appintro.AppIntroActivity

class SplashScreenActivity : AppCompatActivity() {

    private val timer: CountDownTimer by lazy {
        object : CountDownTimer(2500, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, AppIntroActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        setSplashTimer()
    }

    private fun setSplashTimer(){
        timer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}