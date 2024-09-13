package com.smtersoyoglu.shuffleandlearn.ui.learnedwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smtersoyoglu.shuffleandlearn.data.model.Word
import com.smtersoyoglu.shuffleandlearn.databinding.LearnedWordsItemBinding

class LearnedWordsListAdapter(private var learnedWordList: ArrayList<Word>,
                              private val onWordClick: (Word) -> Unit)
    : RecyclerView.Adapter<LearnedWordsListAdapter.LearnedWordListViewHolder>() {

    inner class LearnedWordListViewHolder(private var binding: LearnedWordsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            with(binding) {
                itemLearnedTranslation.text = word.translation
                itemLearnedEnglish.text = word.english
                //itemLearnedImage.setImageResource
                Glide.with(itemView.context).load(word.imageUrl).into(itemLearnedImage)

                root.setOnClickListener {
                    onWordClick(word)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LearnedWordListViewHolder {
        val binding = LearnedWordsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LearnedWordListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LearnedWordListViewHolder,
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