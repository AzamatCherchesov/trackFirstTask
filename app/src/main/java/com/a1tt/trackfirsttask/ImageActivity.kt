package com.a1tt.trackfirsttask

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log

class ImageActivity : AppCompatActivity() {

    private var myApplication : MyApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        myApplication = applicationContext as MyApplication
        myApplication?.activity1 = this
    }

    companion object {
        private const val LOG_TAG = "ImageActivity"
    }

    override fun onBackPressed() {
        myApplication?.countDownTimer = null
        finish()
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.d(LOG_TAG, "onPostResume")

        if (myApplication!!.countDownTimer != null) {
            myApplication!!.countDownTimer!!.cancel()
        }
        myApplication!!.countDownTimer = object : CountDownTimer(myApplication!!.timerCounter, 10) {
            override fun onTick(millisUntilFinished: Long) {
                myApplication!!.timerCounter = millisUntilFinished
            }

            override fun onFinish() {
                onTimer()
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause: !")
        if (myApplication!!.countDownTimer != null) {
            myApplication!!.countDownTimer!!.cancel()
            myApplication!!.countDownTimer = null
        }
    }

    fun onTimer() {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
        finish()
    }
}
