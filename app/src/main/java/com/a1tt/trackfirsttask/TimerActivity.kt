package com.a1tt.trackfirsttask

import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class TimerActivity : AppCompatActivity() {
    private var myApplication: MyApplication? = null

    private lateinit var button: Button
    private lateinit var textView: TextView

    companion object {
        private val LOG_TAG = "TimerActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        myApplication = applicationContext as MyApplication?
        myApplication!!.activity2 = this

        button = findViewById(R.id.button) as Button
        textView = findViewById(R.id.textView) as TextView
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.d(LOG_TAG, "onPostCreate")

        if (myApplication!!.countTask != null) {
            button!!.setText(R.string.button_stop)
        } else {
            button!!.setText(R.string.button_start)
            if (myApplication!!.counter == 1000) {
                textView!!.setText(R.string.s1000)
            } else {
                textView!!.setText(R.string.s0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myApplication!!.activity2 = null
    }

    inner class CountTask constructor(private val myApplication: MyApplication): AsyncTask<Void, Void, Void>() {

        private val s = arrayOf(arrayOf(getString(R.string.s0), getString(R.string.s1), getString(R.string.s2), getString(R.string.s3), getString(R.string.s4), getString(R.string.s5), getString(R.string.s6), getString(R.string.s7), getString(R.string.s8), getString(R.string.s9)), arrayOf(getString(R.string.s00), getString(R.string.s10), getString(R.string.s20), getString(R.string.s30), getString(R.string.s40), getString(R.string.s50), getString(R.string.s60), getString(R.string.s70), getString(R.string.s80), getString(R.string.s90)), arrayOf(getString(R.string.s000), getString(R.string.s100), getString(R.string.s200), getString(R.string.s300), getString(R.string.s400), getString(R.string.s500), getString(R.string.s600), getString(R.string.s700), getString(R.string.s800), getString(R.string.s900)))

        private val s10 = arrayOf(getString(R.string.s10), getString(R.string.s11), getString(R.string.s12), getString(R.string.s13), getString(R.string.s14), getString(R.string.s15), getString(R.string.s16), getString(R.string.s17), getString(R.string.s18), getString(R.string.s19))

        init {
            Log.d(LOG_TAG, "CountTask: CONSTRUCTOR")
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            Log.d(LOG_TAG, "doInBackground")
            while (myApplication!!.counter < 1000) {
                if (isCancelled) {
                    return null
                }
                publishProgress()
                SystemClock.sleep(1000)
                myApplication!!.counter++
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate(*values)
            Log.d(LOG_TAG, "onProgressUpdate: !")
            val counter = myApplication.counter
            val units = counter % 10
            val tens = (counter / 10) % 10
            val hundreds = (counter/ 100) % 10

            val stringUnits: String
            val stringTens: String
            val stringHundreds: String

            if (tens == 1) {
                stringUnits = ""
                stringTens = s10[units]
            } else {
                stringUnits = s[0][units]
                stringTens = s[1][tens]
            }
            stringHundreds = s[2][hundreds]

            val text = "$stringHundreds $stringTens $stringUnits"
            myApplication.activity2?.textView!!.text = text
        }

        override fun onPostExecute(result: Void) {
            Log.d(LOG_TAG, "onPostExecute")
            myApplication.activity2?.textView!!.setText(R.string.s1000)
            myApplication.activity2?.button!!.setText(R.string.button_start)
            myApplication.counter = 1
            myApplication.countTask = null
        }
    }

    fun onButtonStart(view: View) {
        Log.d(LOG_TAG, "onButtonStart")
        if (myApplication!!.countTask == null) {
            Log.d(LOG_TAG, "onButtonStart: start")
            button!!.text = getString(R.string.button_stop)
            textView!!.text = getString(R.string.s1)
            myApplication!!.countTask = CountTask(myApplication!!)
            Log.d(LOG_TAG, "onButtonStart: created count task")
            myApplication!!.countTask?.execute()
        } else {
            Log.d(LOG_TAG, "onButtonStart: stop:" + myApplication!!.countTask?.cancel(true))
            myApplication!!.countTask = null
            myApplication!!.counter = 1
            button!!.text = getString(R.string.button_start)
            textView!!.text = getString(R.string.s0)
        }
    }
}