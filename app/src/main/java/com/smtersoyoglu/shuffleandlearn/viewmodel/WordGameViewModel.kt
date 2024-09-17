package com.smtersoyoglu.shuffleandlearn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearn.data.datasource.WordDataSource
import com.smtersoyoglu.shuffleandlearn.data.model.Word
import com.smtersoyoglu.shuffleandlearn.data.repository.WordRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WordGameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WordRepository(WordDataSource(application))
    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    private val _timer = MutableLiveData<Int>()
    val timer: LiveData<Int> get() = _timer

    private val _correctCount = MutableLiveData<Int>()
    val correctCount: LiveData<Int> get() = _correctCount

    private val _incorrectCount = MutableLiveData<Int>()
    val incorrectCount: LiveData<Int> get() = _incorrectCount

    private var currentWordIndex = 0
    private var remainingTime = 60 // Başlangıç süresi

    private var timerJob: Job? = null
    private var isTimerRunning = false

    init {
        _correctCount.value = 0
        _incorrectCount.value = 0
        fetchWords()
    }

    private fun fetchWords() {
        viewModelScope.launch {
            _wordList.postValue(repository.getWordList().shuffled())
        }
    }

    fun getCurrentWord(): Word? {
        return _wordList.value?.getOrNull(currentWordIndex)
    }

    fun checkWord(userInput: String): Boolean {
        val currentWord = getCurrentWord()
        return if (currentWord != null) {
            if (userInput.equals(currentWord.english, true)) {
                _correctCount.value = _correctCount.value?.plus(1)
                currentWordIndex++ // Sıradaki kelimeye geç
                true  // Doğru cevap
            } else {
                _incorrectCount.value = _incorrectCount.value?.plus(1)
                currentWordIndex++ // Sıradaki kelimeye geç
                false  // Yanlış cevap
            }
        } else {
            false  // Geçerli bir kelime yok
        }
    }


    private fun startTimer() {
        if (isTimerRunning) return // Eğer zaten çalışıyorsa tekrar başlatma
        isTimerRunning = true
        timerJob = viewModelScope.launch {
            while (remainingTime > 0) {
                _timer.postValue(remainingTime)
                delay(1000)
                remainingTime--
            }
            if (remainingTime <= 0) {
                _timer.postValue(0)
                // Zaman dolduğunda yapılacak işlemler
            }
            isTimerRunning = false
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        isTimerRunning = false
    }

    fun resetGame() {
        currentWordIndex = 0
        remainingTime = 60
        _correctCount.value = 0
        _incorrectCount.value = 0
        _timer.value = remainingTime
        pauseTimer() // Timer'ı durdur
        startTimer() // Timer'ı yeniden başlat
    }
}
