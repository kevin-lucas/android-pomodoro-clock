package br.com.kevincode.pomodoroclock

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var chTimer: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val play = findViewById<ImageButton>(R.id.bt_play)
        val stop = findViewById<ImageButton>(R.id.bt_stop)
        val pause = findViewById<ImageButton>(R.id.bt_pause)
        val session = findViewById<TextView>(R.id.tv_session)
        val chTimer = findViewById<Chronometer>(R.id.ch_timer)

        var count = 0L
        var isWorkSession = true
        var countSession = 1

        session.text = "Sessão $countSession"
        chTimer.text = "25:00"

        play.setOnClickListener {
            val minutes = if (count > 0) count else 25 * 60000L
            chTimer.base = SystemClock.elapsedRealtime() + minutes
            chTimer.isCountDown = true
            chTimer.start()
        }

        stop.setOnClickListener {

            chTimer.stop()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Resetar Sessão")
            builder.setMessage("Deseja pular a sessão atual?")
            builder.setPositiveButton("Sim") { _, _ ->

                if (isWorkSession) {
                    chTimer.base = SystemClock.elapsedRealtime() + (5 * 60000L)
                    session.text = "Descanso"
                    setTheme(R.style.Theme_PomodoroClock_Rest)
                    countSession += 1
                    isWorkSession = false

                } else {
                    chTimer.base = SystemClock.elapsedRealtime() + (25 * 60000L)
                    session.text = "Sessão $countSession"
                    isWorkSession = true
                }


            }
            builder.setNegativeButton("Não") { _, _ ->
                chTimer.start()
            }
            builder.show()
        }

        pause.setOnClickListener {
            chTimer.stop()
        }

        chTimer.setOnChronometerTickListener {

            count = (SystemClock.elapsedRealtime() - chTimer.base) * -1

            if (count < 0L) {

                chTimer.stop()

                if (isWorkSession) {
                    chTimer.base = SystemClock.elapsedRealtime() + (5 * 60000L)
                    session.text = "Descanso"
                    countSession += 1
                    isWorkSession = false

                } else {
                    chTimer.base = SystemClock.elapsedRealtime() + (25 * 60000L)
                    session.text = "Sessão $countSession"
                    setTheme(R.style.Theme_PomodoroClock_Rest)
                    isWorkSession = true
                }
            }
        }
    }
}

