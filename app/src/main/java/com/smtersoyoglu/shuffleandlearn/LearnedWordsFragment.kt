package com.smtersoyoglu.shuffleandlearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentLearnedWordsBinding
import kotlinx.coroutines.launch

class LearnedWordsFragment : Fragment() {

    private  var _binding : FragmentLearnedWordsBinding? = null
    private val binding get() = _binding!!

    private lateinit var learnedWordsListAdapter: LearnedWordsListAdapter
    private lateinit var viewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLearnedWordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        learnedWordsListAdapter = LearnedWordsListAdapter(arrayListOf()) { word ->
            val action = LearnedWordsFragmentDirections.actionLearnedWordsFragmentToDetailFragment(word)
            findNavController().navigate(action)
        }

        binding.learnedWordsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.learnedWordsRecyclerView.adapter = learnedWordsListAdapter

        viewModel.learnedWordList.observe(viewLifecycleOwner) { learnedWords ->
            learnedWordsListAdapter.updateWordList(learnedWords)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchLearnedWords()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
