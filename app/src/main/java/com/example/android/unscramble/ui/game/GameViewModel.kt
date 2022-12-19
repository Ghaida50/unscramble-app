/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {

    private var _score = 0
    private var _counter = 0
    private var _currentScrambledWord = "test"
    private var _currentWordCount = 0

    val score: Int
        get() = _score

    val counter: Int
        get() = _counter

    val currentScrambledWord: String
        get() = _currentScrambledWord

    val currentWordCount: Int
        get() = _currentWordCount

    // hold a list of words you use in the game, to avoid repetitions
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String // counter

    init {
        getNextWord()
    }

    private fun getNextWord() {

        // random word
        currentWord = allWordsList.random()

        // temp word
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        //  shuffled order of characters is the same as the original word
        while (String(tempWord).equals(currentWord, false)) {
            // if true
            // keep shuffle()
            tempWord.shuffle()
        }// end while

        // check if a word has been used already
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }// end else

    } // end getNextWord

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }//end nextWord

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }// end increaseScore

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        } else
            return false
    }// end isUserWordCorrect

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }// end reinitializeData


}// end view model