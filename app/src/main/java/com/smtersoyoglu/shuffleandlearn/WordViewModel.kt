package com.smtersoyoglu.shuffleandlearn
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository()
    private val sharedPreferences = getApplication<Application>().getSharedPreferences("learned_words", Context.MODE_PRIVATE)

    private val _wordList = MutableLiveData<ArrayList<Word>>()
    val wordList: LiveData<ArrayList<Word>> get() = _wordList

    private val _learnedWordList = MutableLiveData<ArrayList<Word>>()
    val learnedWordList: LiveData<ArrayList<Word>> get() = _learnedWordList

    init {
        fetchWords()
        fetchLearnedWords()
    }

    fun fetchWords() {
        val allWords = repository.getWordList()
        val learnedWordsIds = getLearnedWordsIds()
        _wordList.value = allWords.filter { !learnedWordsIds.contains(it.id) } as ArrayList<Word>
    }

    fun fetchLearnedWords() {
        val learnedWordsIds = getLearnedWordsIds()
        val allWords = repository.getWordList()
        _learnedWordList.value = allWords.filter { learnedWordsIds.contains(it.id) } as ArrayList<Word>
    }

    fun isWordLearned(wordId: Int): Boolean {
        return sharedPreferences.contains("word_${wordId}_learned")
    }

    fun markWordAsLearned(word: Word) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("word_${word.id}_learned", true)
        editor.apply()

        fetchWords()  // Refresh word list
        fetchLearnedWords()  // Refresh learned words list
    }

    fun markWordAsUnlearned(word: Word) {
        val editor = sharedPreferences.edit()
        editor.remove("word_${word.id}_learned")
        editor.apply()

        fetchWords()  // Refresh word list
        fetchLearnedWords()  // Refresh learned words list
    }

    private fun getLearnedWordsIds(): Set<Int> {
        return sharedPreferences.all.keys
            .filter { it.startsWith("word_") }
            .map { it.removePrefix("word_").removeSuffix("_learned").toInt() }
            .toSet()
    }
    fun shuffleWords() {
        _wordList.value = _wordList.value?.shuffled() as ArrayList<Word>
    }
}


