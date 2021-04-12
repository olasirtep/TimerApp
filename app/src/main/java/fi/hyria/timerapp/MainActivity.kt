package fi.hyria.timerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var timerMillis: Long = 0
    var startMillis: Long = 0
    var timerRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable{
            while(true) {
                Thread.sleep(10)
                if (timerRunning) {
                    timerMillis = System.currentTimeMillis()-startMillis
                    runOnUiThread(Runnable {
                        updateTimerView()
                    })
                }
            }
        }).start()
    }

    fun startTimer(view: View) {
        startMillis = System.currentTimeMillis()
        timerRunning = true
    }

    fun stopTimer(view: View) {
        timerRunning = false
    }

    fun updateTimerView() {
        var millis = timerMillis
        var hours: String = addLeadingZeros((millis/3600000), 2)
        millis %= 3600000
        var minutes: String = addLeadingZeros((millis/60000), 2)
        millis %= 60000
        var seconds: String = addLeadingZeros((millis/1000), 2)
        millis %= 1000

        findViewById<TextView>(R.id.timerView).text = hours+":"+minutes+":"+seconds+"."+addLeadingZeros(millis, 3)
    }

    fun addLeadingZeros(time: Long, length: Int): String {
        var zeroes: Int = length-time.toString().length
        var leadingZeroes: String = ""
        for (i in 1..zeroes) {
            leadingZeroes += "0"
        }
        return leadingZeroes+time.toString()
    }
}