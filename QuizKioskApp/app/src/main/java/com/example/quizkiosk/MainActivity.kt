package com.example.quizkiosk

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var timerText: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button

    private var currentQuestion = 0
    private var questions = listOf(
        QuizQuestion("“સેવા, સુશાસન અને ગરીબ કલ્યાણ” ના ૧૧ વર્ષ કયા પ્રધાનમંત્રીના કાર્યકાળ સાથે જોડાયેલા છે?",
            listOf("ડૉ. મનમોહન સિંહ", "શ્રી નરેન્દ્ર મોદી", "લાલ કૃષ્ણ આડવાણી", "રાહુલ ગાંધી"), 1),
        QuizQuestion("“પ્રધાનમંત્રી જનધન યોજના” ક્યારે શરૂ કરવામાં આવી હતી?",
            listOf("2010", "2012", "2014", "2016"), 2)
    )

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionText = findViewById(R.id.question_text)
        timerText = findViewById(R.id.timer_text)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        showQuestion()
    }

    private fun showQuestion() {
        if (currentQuestion >= questions.size) {
            questionText.text = "ક્વિઝ પૂરી થઈ!"
            return
        }

        val q = questions[currentQuestion]
        questionText.text = q.question
        option1.text = q.options[0]
        option2.text = q.options[1]
        option3.text = q.options[2]
        option4.text = q.options[3]

        val buttons = listOf(option1, option2, option3, option4)
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (index == q.correctIndex) {
                    button.setBackgroundColor(0xFF4CAF50.toInt()) // Green
                } else {
                    button.setBackgroundColor(0xFFF44336.toInt()) // Red
                }
                countDownTimer?.cancel()
                currentQuestion++
                button.postDelayed({ showQuestion() }, 2000)
            }
        }

        startTimer()
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerText.text = "સમય: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                currentQuestion++
                showQuestion()
            }
        }.start()
    }
}

data class QuizQuestion(val question: String, val options: List<String>, val correctIndex: Int)
