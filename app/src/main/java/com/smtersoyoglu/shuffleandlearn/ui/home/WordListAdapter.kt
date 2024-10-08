package com.smtersoyoglu.shuffleandlearn.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smtersoyoglu.shuffleandlearn.R
import com.smtersoyoglu.shuffleandlearn.data.model.Word
import com.smtersoyoglu.shuffleandlearn.databinding.WordItemBinding

class WordListAdapter(private var wordList: ArrayList<Word>,
                      private val onWordClick: (Word) -> Unit)
    : RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    inner class WordListViewHolder(private var binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            with(binding) {
                itemTranslation.text = word.translation
                itemEnglish.text = word.english
                //itemImage.setImageResource(word.imageResId)
                Glide.with(itemView.context)
                    .load(word.imageUrl)
                    .error(R.drawable.img_not_available)
                    .into(itemImage)


                root.setOnClickListener {
                    onWordClick(word)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordListViewHolder {
        val binding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    fun updateWordList(newWordList: List<Word>) {
        val diffCallback = WordDiffCallback(wordList, newWordList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        wordList.clear()
        wordList.addAll(newWordList)
        diffResult.dispatchUpdatesTo(this)
    }

}

