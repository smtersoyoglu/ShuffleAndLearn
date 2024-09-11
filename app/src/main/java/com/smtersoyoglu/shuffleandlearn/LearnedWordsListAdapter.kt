package com.smtersoyoglu.shuffleandlearn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smtersoyoglu.shuffleandlearn.databinding.LearnedWordsItemBinding

class LearnedWordsListAdapter(private var learnedWordList: ArrayList<Word>,
                              private val onWordClick: (Word) -> Unit)
    : RecyclerView.Adapter<LearnedWordsListAdapter.LearnedWordListViewHolder>() {

    inner class LearnedWordListViewHolder(private var binding: LearnedWordsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            with(binding) {
                itemLearnedTranslation.text = word.translation
                itemLearnedEnglish.text = word.english
                itemLearnedImage.setImageResource(word.imageResId)

                root.setOnClickListener {
                    onWordClick(word)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LearnedWordsListAdapter.LearnedWordListViewHolder {
        val binding = LearnedWordsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LearnedWordListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LearnedWordsListAdapter.LearnedWordListViewHolder,
        position: Int
    ) {
        holder.bind(learnedWordList[position])
    }

    override fun getItemCount(): Int {
        return learnedWordList.size
    }

    fun updateWordList(newWordList: ArrayList<Word>) {
        learnedWordList = newWordList
        notifyDataSetChanged()
    }
}