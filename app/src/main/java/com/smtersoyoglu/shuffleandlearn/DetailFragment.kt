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
    private val args: DetailFragmentArgs by navArgs() // Safeargs ile argümanları alıyoruz



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)


        // Argümanları al
        val word = args.word

        // Veriyi binding'e ata
        binding.detailTranslationText.text = word.translation
        binding.detailEnglishText.text = word.english
        binding.detailImageView.setImageResource(word.imageResId)

        if (word.isLearned) {
            binding.learnedButton.text = "UnLearned"
        } else {
            binding.learnedButton.text = "Learned"
        }

        // Learn/Unlearn işlemi
        binding.learnedButton.setOnClickListener {
            if (word.isLearned) {
                viewModel.markWordAsUnlearned(word)
                binding.learnedButton.text = "Learned"
            } else {
                viewModel.markWordAsLearned(word)
                binding.learnedButton.text = "UnLearned"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}