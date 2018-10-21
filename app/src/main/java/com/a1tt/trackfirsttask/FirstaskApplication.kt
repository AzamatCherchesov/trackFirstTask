package com.a1tt.trackfirsttask

import android.app.Application
import android.os.CountDownTimer

class MyApplication : Application() {

    var activity1: ImageActivity? = null
    var timerCounter: Long = 2000
    var countDownTimer: CountDownTimer? = null

    var activity2: TimerActivity? = null
    var countTask: TimerActivity.CountTask? = null
    var counter = 1

}