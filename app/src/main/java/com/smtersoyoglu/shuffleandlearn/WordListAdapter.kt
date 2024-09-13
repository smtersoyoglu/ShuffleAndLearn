package com.smtersoyoglu.shuffleandlearn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                    .placeholder(R.drawable.image_loading)
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
    ): WordListAdapter.WordListViewHolder {
        val binding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordListAdapter.WordListViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    fun updateWordList(newWordList: ArrayList<Word>) {
        wordList = newWordList
        notifyDataSetChanged()
    }
}

