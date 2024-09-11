package com.smtersoyoglu.shuffleandlearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WordViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        val word = args.word
        binding.detailTranslationText.text = word.translation
        binding.detailEnglishText.text = word.english
        binding.detailImageView.setImageResource(word.imageResId)
        // İngilizce kelimenin altındaki cümleyi göstermek için
        binding.detailExampleSentenceText.text = word.sentence

        // Check if the word is learned
        val isLearned = viewModel.isWordLearned(word.id)
        binding.learnedButton.text = if (isLearned) "UnLearned" else "Learned"

        binding.learnedButton.setOnClickListener {
            if (isLearned) {
                viewModel.markWordAsUnlearned(word)
            } else {
                viewModel.markWordAsLearned(word)
            }
            // Navigate back to previous fragment
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
