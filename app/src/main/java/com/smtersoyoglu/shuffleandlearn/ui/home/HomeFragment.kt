package com.smtersoyoglu.shuffleandlearn.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.smtersoyoglu.shuffleandlearn.viewmodel.WordViewModel
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordListAdapter: WordListAdapter
    private val viewModel: WordViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("learned_words", Context.MODE_PRIVATE)

        wordListAdapter = WordListAdapter(arrayListOf()) { word ->
            // Handle word click event
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(word)
            findNavController().navigate(action)
        }

        binding.wordRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.wordRecyclerView.adapter = wordListAdapter

        // Swipe to Refresh Setup
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.shuffleWords()
            binding.swipeRefreshLayout.isRefreshing = false

        }

        viewModel.wordList.observe(viewLifecycleOwner) { wordList ->
            wordListAdapter.updateWordList(wordList)
            // SwipeRefresh animasyonunu durdur
            binding.swipeRefreshLayout.isRefreshing = false
            // RecyclerView en üste kaydır
            binding.wordRecyclerView.scrollToPosition(0)
        }

        /*
        // Refresh the list when returning from DetailFragment
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchWords()
        }
        */

        // Yenileme işlemini başlat
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchWordsFromJson()
        }

    }

    override fun onResume() {
        super.onResume()
        // Ekrana geri dönüldüğünde listeyi güncelle
        viewModel.fetchWordsFromJson()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // ViewBinding nesnesini temizle
        _binding = null
    }

}