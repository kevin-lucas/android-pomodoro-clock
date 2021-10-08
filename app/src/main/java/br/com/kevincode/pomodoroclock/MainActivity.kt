package br.com.kevincode.pomodoroclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var clock: MyCountDownTimer
    private var time: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = findViewById<TextView>(R.id.tv_timer)
        val play = findViewById<ImageButton>(R.id.bt_play)
        val stop = findViewById<ImageButton>(R.id.bt_stop)
        val pause = findViewById<ImageButton>(R.id.bt_pause)

        play.setOnClickListener {
            if (time == 0L){
                clock = MyCountDownTimer(this, timer, 25 * 60000L, 1000)
                clock.cancel()
                clock.start()
            } else {
                clock = MyCountDownTimer(this, timer, time, 1000)
                clock.start()
            }
        }

        stop.setOnClickListener {
            clock.cancel()
        }

        pause.setOnClickListener {
            time = clock.millisInFuture
            clock.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (clock != null) {
            clock.cancel()
        }
    }
}