package com.smtersoyoglu.shuffleandlearn.ui.learnedwords

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
import com.smtersoyoglu.shuffleandlearn.data.model.Word
import com.smtersoyoglu.shuffleandlearn.viewmodel.WordViewModel
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentLearnedWordsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LearnedWordsFragment : Fragment() {

    private  var _binding : FragmentLearnedWordsBinding? = null
    private val binding get() = _binding!!

    private lateinit var learnedWordsListAdapter: LearnedWordsListAdapter
    private val viewModel: WordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLearnedWordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        learnedWordsListAdapter = LearnedWordsListAdapter(arrayListOf()) { word ->
            val action = LearnedWordsFragmentDirections.actionLearnedWordsFragmentToDetailFragment(word)
            findNavController().navigate(action)
        }

        binding.learnedWordsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.learnedWordsRecyclerView.adapter = learnedWordsListAdapter

        viewModel.learnedWordList.observe(viewLifecycleOwner) { learnedWords ->
            learnedWordsListAdapter.updateWordList(learnedWords)
            updateUIBasedOnData(learnedWords)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchLearnedWords()
        }

    }

    private fun updateUIBasedOnData(learnedWords: List<Word>) {
        if (learnedWords.isEmpty()) {
            binding.apply {
                // Liste boş ise animasyonu göster, RecyclerView'u gizle
                learnedWordsRecyclerView.visibility = View.GONE
                emptyAnimView.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                // Liste dolu ise RecyclerView'u göster, animasyonu gizle
                learnedWordsRecyclerView.visibility = View.VISIBLE
                emptyAnimView.visibility = View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
