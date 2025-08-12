package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

data class Question(val textResId: Int, val answer: Boolean)

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val KEY_INDEX = "current_index"
    private val KEY_CHEATER = "is_cheater"

    // Your question bank
    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var currentIndex: Int
        get() = savedStateHandle.get<Int>(KEY_INDEX) ?: 0
        set(value) { savedStateHandle[KEY_INDEX] = value }

    var isCheater: Boolean
        get() = savedStateHandle.get<Boolean>(KEY_CHEATER) ?: false
        set(value) { savedStateHandle[KEY_CHEATER] = value }


    fun currentQuestionText() = questionBank[currentIndex].textResId
    fun currentAnswer() = questionBank[currentIndex].answer



    fun moveToNext() { currentIndex = (currentIndex + 1) % questionBank.size }
    fun moveToPrev() {
        currentIndex = if (currentIndex == 0) questionBank.size - 1 else currentIndex - 1
    }
}
