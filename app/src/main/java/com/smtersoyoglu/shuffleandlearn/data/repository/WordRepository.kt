package com.smtersoyoglu.shuffleandlearn.data.repository

import com.smtersoyoglu.shuffleandlearn.data.datasource.WordDataSource
import com.smtersoyoglu.shuffleandlearn.data.model.Word

class WordRepository(private val dataSource: WordDataSource) {

    fun getWordList(): ArrayList<Word> {
        return dataSource.getWordListFromJson()
    }
    /*
    fun getWordList(): ArrayList<Word> {
        return arrayListOf(
            Word(1, "Ördek", "Duck", R.drawable.duck_card, sentence = "The duck is swimming in the pond."),
            Word(2, "Köpek", "Dog", R.drawable.dog_card, sentence = "The dog is playing with a ball in the garden."),
            Word(3, "Kedi", "Cat", R.drawable.cat_card, sentence = "The cat is sleeping on the windowsill."),
            Word(4, "Elma", "Apple", R.drawable.apple_card, sentence = "She is eating a red apple."),
            Word(5, "Ağaç", "Tree", R.drawable.tree_card, sentence = "The tree is full of green leaves."),
            Word(6, "Şişe", "Bottle", R.drawable.bottle_card, sentence = "He is drinking water from a bottle."),
            Word(7, "Saat", "Watch", R.drawable.watch_card, sentence = "She looked at her watch to check the time."),
            Word(8, "Sandalye", "Chair", R.drawable.chair_card, sentence = "He sat down on the wooden chair."),
            Word(9, "Diş fırçası", "Toothbrush", R.drawable.toothbrush_card, sentence = "She brushes her teeth with a new toothbrush.")
        )
    }
     */

}
