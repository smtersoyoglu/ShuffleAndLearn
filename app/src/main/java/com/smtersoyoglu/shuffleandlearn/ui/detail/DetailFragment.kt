package com.smtersoyoglu.shuffleandlearn.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.smtersoyoglu.shuffleandlearn.viewmodel.WordViewModel
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WordViewModel by viewModels()
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


        val word = args.word
        binding.detailTranslationText.text = word.translation
        binding.detailEnglishText.text = word.english
        //binding.detailImageView.setImageResource(word.imageUrl)
        // Glide ile imageUrl'den resmi yükleme
        Glide.with(this).load(word.imageUrl).into(binding.detailImageView)

        // İngilizce kelimenin altındaki cümleyi göstermek için
        binding.detailExampleSentenceText.text = word.sentence

        // Geri ikonuna tıklanınca geri gitme işlemi
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }


        // Check if the word is learned
        val isLearned = viewModel.isWordLearned(word.id)
        binding.learnedButton.text = if (isLearned) "UnLearned" else "Learned"

        binding.learnedButton.setOnClickListener {

            if (isLearned) {
                viewModel.markWordAsUnlearned(word)
                Toast.makeText(requireContext(), "Word removed from learned list", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.markWordAsLearned(word)
                Toast.makeText(requireContext(), "Word learned", Toast.LENGTH_SHORT).show()
            }

            // Navigate back to previous fragment
            //findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
