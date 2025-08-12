package com.bignerdranch.android.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false
    private var answerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerShown = savedInstanceState?.getBoolean(KEY_ANSWER_SHOWN, false) ?: false

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        // if user already revealed before a rotation keep result set
        if (answerShown) {
            showAnswerText()
            setAnswerShownResult(true)
        }

        showAnswerButton.setOnClickListener {
            showAnswerText()
            answerShown = true
            setAnswerShownResult(true)
        }
    }

    private fun showAnswerText() {
        val textRes = if (answerIsTrue) R.string.true_button else R.string.false_button
        answerTextView.setText(textRes)
    }

    private fun setAnswerShownResult(shown: Boolean) {
        val data = Intent().apply { putExtra(EXTRA_ANSWER_SHOWN, shown) }
        setResult(RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, answerShown)
    }

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
        private const val KEY_ANSWER_SHOWN = "key_answer_shown"

        fun newIntent(ctx: Context, answerIsTrue: Boolean): Intent =
            Intent(ctx, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
    }
}
