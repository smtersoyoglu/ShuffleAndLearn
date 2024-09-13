package com.smtersoyoglu.shuffleandlearn.viewmodel
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smtersoyoglu.shuffleandlearn.data.datasource.WordDataSource
import com.smtersoyoglu.shuffleandlearn.data.model.Word
import com.smtersoyoglu.shuffleandlearn.data.repository.WordRepository
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(WordDataSource(application))

    private val _wordList = MutableLiveData<ArrayList<Word>>()
    val wordList: LiveData<ArrayList<Word>> get() = _wordList

    private val _learnedWordList = MutableLiveData<ArrayList<Word>>()
    val learnedWordList: LiveData<ArrayList<Word>> get() = _learnedWordList

    private val sharedPreferences = getApplication<Application>().getSharedPreferences("learned_words", Context.MODE_PRIVATE)

    init {
        fetchWordsFromJson()
        fetchLearnedWords()
    }

    fun fetchWordsFromJson() {
        val allWords = repository.getWordList()
        val learnedWords = getLearnedWordsFromPrefs()

        val filteredWords = allWords.filter { word ->
            learnedWords.none { it.id == word.id }
        }

        _wordList.postValue(ArrayList(filteredWords))
    }

    fun shuffleWords() {
        _wordList.value?.let { wordList ->
            wordList.shuffle()
            _wordList.postValue(ArrayList(wordList))
        }
    }

    fun fetchLearnedWords() {
        viewModelScope.launch {
            val learnedWords = getLearnedWordsFromPrefs()
            _learnedWordList.postValue(learnedWords)
        }
    }

    fun markWordAsLearned(word: Word) {
        val learnedWords = getLearnedWordsFromPrefs()

        if (learnedWords.none { it.id == word.id }) {
            val updatedList = ArrayList(learnedWords).apply {
                add(word)
            }
            saveLearnedWordsToPrefs(updatedList)
            _learnedWordList.postValue(updatedList)
            removeWordFromWordList(word)
        }

    }

    fun markWordAsUnlearned(word: Word) {

        val learnedWords = getLearnedWordsFromPrefs().apply {
            remove(word)
        }
        saveLearnedWordsToPrefs(learnedWords)
        _learnedWordList.postValue(learnedWords)
        addWordToWordList(word)
    }

    fun isWordLearned(wordId: Int): Boolean {
        val learnedWords = getLearnedWordsFromPrefs()
        return learnedWords.any { it.id == wordId }
    }

    private fun getLearnedWordsFromPrefs(): ArrayList<Word> {
        val learnedWordsJson = sharedPreferences.getString("learned_words", "[]")
        val learnedWordsList = ArrayList<Word>()
        val jsonArray = JSONArray(learnedWordsJson)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val english = jsonObject.getString("english")
            val translation = jsonObject.getString("translation")
            val imageUrl = jsonObject.getString("image_url")
            val sentence = jsonObject.optString("sentence", "")

            learnedWordsList.add(Word(id, translation, english, imageUrl, sentence = sentence))
        }

        return learnedWordsList
    }

    private fun saveLearnedWordsToPrefs(learnedWords: ArrayList<Word>) {
        val jsonArray = JSONArray()

        for (word in learnedWords) {
            val jsonObject = JSONObject()
            jsonObject.put("id", word.id)
            jsonObject.put("english", word.english)
            jsonObject.put("translation", word.translation)
            jsonObject.put("image_url", word.imageUrl)
            jsonObject.put("sentence", word.sentence)
            jsonArray.put(jsonObject)
        }

        sharedPreferences.edit().putString("learned_words", jsonArray.toString()).apply()
    }

    private fun removeWordFromWordList(word: Word) {
        _wordList.value?.let { wordList ->
            wordList.remove(word)
            _wordList.postValue(ArrayList(wordList))
        }
    }

    private fun addWordToWordList(word: Word) {
        _wordList.value?.let { wordList ->
            wordList.add(word)
            _wordList.postValue(ArrayList(wordList))
        }
    }
}

