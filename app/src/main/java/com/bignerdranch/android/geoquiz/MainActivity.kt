// CSC202 Mobile App Development GeoQuiz
//Name: Elisha McArthur
//Student ID: 1185600
//Date 21/08/2025
//



package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by viewModels()

    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var cheatButton: Button

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val cheated = result.data?.getBooleanExtra(
            CheatActivity.EXTRA_ANSWER_SHOWN, false
        ) ?: false
        if (cheated) quizViewModel.isCheater = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bind views
        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)   // ImageButton in XML
        prevButton = findViewById(R.id.prev_button)   // ImageButton in XML
        cheatButton = findViewById(R.id.cheat_button)

        updateQuestion()

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            quizViewModel.isCheater = false
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            quizViewModel.isCheater = false
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            val intent = CheatActivity.newIntent(this, quizViewModel.currentAnswer())
            cheatLauncher.launch(intent)
        }
    }

    private fun updateQuestion() {
        questionTextView.setText(quizViewModel.currentQuestionText())
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentAnswer()
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
