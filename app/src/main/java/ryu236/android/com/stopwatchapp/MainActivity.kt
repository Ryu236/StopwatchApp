/*
 * Copyright (c) 2018 Ryu236
 */

package ryu236.android.com.stopwatchapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue: Int = 0
    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         *１秒ごとに加算する処理
         */
        val runnable = object : Runnable {
            override fun run() {
                // TextViewを更新
                TimeToText(timeValue).let {
                    timeText.text = it
                }
                handler.postDelayed(this, 1000)
                timeValue++
            }
        }

        /*
         *  Buttonの処理
         */
        start.setOnClickListener {
                if (flag) {
                    handler.removeCallbacks(runnable)
                    start.text = "START"
                    flag = false
                } else {
                    handler.post(runnable)
                    start.text = "STOP"
                    flag = true
                }
        }

        reset.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            // resetボタンを押したときにstartボタンをリセットする
            start.text = "START"
            flag = false
            TimeToText().let {
                timeText.text = it
            }
        }
    }

    /*
     *   数値を00:00:00形式の文字列に変換する関数
     */
    private fun TimeToText(time: Int = 0): String {
        if (time == 0) {
            return "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            return "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}
