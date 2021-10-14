package br.com.kevincode.pomodoroclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var chTimer: Chronometer
    private lateinit var session: TextView
    private lateinit var play: ImageButton
    private lateinit var stop: ImageButton
    private lateinit var pause: ImageButton

    var count = 0L
    var isWorkSession = true
    var countSession = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play = findViewById<ImageButton>(R.id.bt_play)
        stop = findViewById<ImageButton>(R.id.bt_stop)
        pause = findViewById<ImageButton>(R.id.bt_pause)
        session = findViewById<TextView>(R.id.tv_session)
        chTimer = findViewById<Chronometer>(R.id.ch_timer)

        initTimer()
        setOnListenerTimer()
        setOnClickListenerButtons()
    }

    private fun initTimer() {
        chTimer.base = SystemClock.elapsedRealtime() + (5 * 60000L)

        session.text = "Sessão $countSession"
        chTimer.text = "25:00"
    }

    private fun setOnClickListenerButtons() {
        play.setOnClickListener {
            startTimer()
        }

        stop.setOnClickListener {
            stopTimer()
        }

        pause.setOnClickListener {
            pauseTimer()
        }
    }

    private fun startTimer() {
        val minutes = if (count > 0) count else 25 * 60000L
        chTimer.base = SystemClock.elapsedRealtime() + minutes
        chTimer.isCountDown = true
        chTimer.start()
        Toast.makeText(this, getString(R.string.timer_status_started), Toast.LENGTH_SHORT).show()
    }

    private fun stopTimer() {
        chTimer.stop()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resetar Sessão")
        builder.setMessage("Deseja sair da sessão atual?")
        builder.setPositiveButton("Sim") { _, _ ->

            if (isWorkSession) {
                chTimer.base = SystemClock.elapsedRealtime() + (5 * 60000L)
                session.text = "Descanso"
                countSession += 1
                isWorkSession = false

            } else {
                chTimer.base = SystemClock.elapsedRealtime() + (25 * 60000L)
                session.text = "Sessão $countSession"
                isWorkSession = true
            }

            Toast.makeText(this, getString(R.string.timer_status_restore), Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("Não") { _, _ ->
            chTimer.start()
        }
        builder.show()
    }

    private fun pauseTimer() {
        chTimer.stop()
        Toast.makeText(this, getString(R.string.timer_status_paused), Toast.LENGTH_SHORT).show()
    }

    private fun setOnListenerTimer() {
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
                    isWorkSession = true
                }
            }
        }
    }
}

