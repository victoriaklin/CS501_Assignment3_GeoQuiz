package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun initiallyProvidesFirstQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBank() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun isCheaterTrueWhenCheatActivityReturnsTrue() {
        // Click the Cheat button to simulate launching CheatActivity
        onView(withId(R.id.cheat_button)).perform(click())
        onView(withId(R.id.show_answer_button)).perform((click()))
        // Check that CheatActivity is launched and returns true

        // After returning from CheatActivity, check the value of isCheater in ViewModel
        scenario.onActivity { activity ->
            assertEquals(true, activity.gettQuizViewModel().isCheater)
        }
    }

    @Test
    fun isCheaterFalseWhenCheatActivityReturnsFalse() {
        scenario.onActivity { activity ->
            assertEquals(false, activity.gettQuizViewModel().isCheater)
        }
    }
}
