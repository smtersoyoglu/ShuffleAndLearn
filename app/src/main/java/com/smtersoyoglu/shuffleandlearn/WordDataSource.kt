package com.smtersoyoglu.shuffleandlearn

import android.content.Context
import org.json.JSONArray

class WordDataSource(private val context: Context) {

    fun getWordListFromJson(): ArrayList<Word> {
        val wordList = ArrayList<Word>()
        val jsonString = context.assets.open("words.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val english = jsonObject.getString("english")
            val translation = jsonObject.getString("translation")
            val imageUrl = jsonObject.getString("image_url")
            val sentence = jsonObject.optString("sentence", "")

            wordList.add(Word(id, translation, english, imageUrl, sentence = sentence))
        }

        return wordList
    }
}