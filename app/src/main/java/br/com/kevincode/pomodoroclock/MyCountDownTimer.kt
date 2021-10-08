package br.com.kevincode.pomodoroclock

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MyCountDownTimer(
    var context: Context,
    var textView: TextView,
    var millisInFuture: Long,
    var countDownInterval: Long
) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        Log.i("Script", "Timer ${millisUntilFinished}")
        millisInFuture = millisUntilFinished
        val minutes = getCorrectTimer(true, millisUntilFinished)
        val seconds = getCorrectTimer(false, millisUntilFinished)
        textView.text = "$minutes : $seconds"
    }

    // Chamado quando acabar a contagem regresiva
    override fun onFinish() {
        millisInFuture -= 1000
        val minutes = getCorrectTimer(true, millisInFuture)
        val seconds = getCorrectTimer(false, millisInFuture)
        textView.text = "$minutes : $seconds"
    }

    private fun getCorrectTimer(isMinute: Boolean, millisUntilFinished: Long): String {
        val constCalendar = if (isMinute) Calendar.MINUTE else Calendar.SECOND
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millisUntilFinished
        return if (calendar.get(constCalendar) < 10)
            "0${calendar.get(constCalendar)}" else "${calendar.get(constCalendar)}"
    }
}