package com.smtersoyoglu.shuffleandlearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordListAdapter: WordListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordList = getWordList()
        wordListAdapter = WordListAdapter(wordList)

        binding.wordRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wordRecyclerView.adapter = wordListAdapter


    }

    private fun getWordList(): ArrayList<Word> {
        return arrayListOf(
            Word(1, "Ördek", "Duck", R.drawable.duck_card),
            Word(2, "Köpek", "Dog", R.drawable.dog_card),
            Word(3, "Kedi", "Cat", R.drawable.cat_card),
            Word(4, "Elma", "Apple", R.drawable.apple_card),
            Word(5, "Ağaç", "Tree", R.drawable.tree_card),
            Word(6, "Şişe", "Bottle", R.drawable.bottle_card),
            Word(7, "Saat", "Watch", R.drawable.watch_card),
            Word(8, "Sandalye", "Chair", R.drawable.chair_card),
            Word(9, "Diş fırçası", "Toothbrush", R.drawable.toothbrush_card),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding nesnesini temizle
        _binding = null
    }

}