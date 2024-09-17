package com.smtersoyoglu.shuffleandlearn.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.smtersoyoglu.shuffleandlearn.data.model.Word

class WordDiffCallback(
    private val oldList: List<Word>,
    private val newList: List<Word>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Eğer her bir öğenin benzersiz bir kimliği varsa karşılaştırma yapın, örn. ID
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Eğer öğelerin içeriği aynıysa true dön
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
