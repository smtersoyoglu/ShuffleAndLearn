package com.smtersoyoglu.shuffleandlearn

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentWordGameBinding

class WordGameFragment : Fragment() {

    private var _binding: FragmentWordGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WordGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WordGameViewModel::class.java)

        // Timer'ı gözlemle
        viewModel.timer.observe(viewLifecycleOwner) { remainingTime ->
            binding.timerText.text = formatTime(remainingTime)
            if (remainingTime <= 0) {
                onTimeUp()
            }
        }

        // Bilinen kelimeleri gözlemle
        viewModel.correctCount.observe(viewLifecycleOwner) { correctCount ->
            binding.trueText.text = correctCount.toString()
        }

        // Bilinmeyen kelimeleri gözlemle
        viewModel.incorrectCount.observe(viewLifecycleOwner) { incorrectCount ->
            binding.falseText.text = incorrectCount.toString()
        }

        // İlk kelimeyi göster
        viewModel.wordList.observe(viewLifecycleOwner) { wordList ->
            if (wordList.isNotEmpty()) {
                updateWord()
            } else {
                binding.turkishWord.text = "Kelime Yükleniyor..."
            }
        }

        // Butona tıklama olayı
        binding.submitButton.setOnClickListener {
            val userInput = binding.englishTranslation.text.toString()
            binding.lottieAnimationView.visibility = View.VISIBLE // Animasyonu göster

            if (viewModel.checkWord(userInput)) {
                // Doğru cevap: happy animasyonu göster
                binding.lottieAnimationView.setAnimation(R.raw.anim_happy)
            } else {
                // Yanlış cevap: shake animasyonu göster
                shakeView(binding.englishTranslation)
                binding.lottieAnimationView.setAnimation(R.raw.anim_sad)

            }
            binding.lottieAnimationView.playAnimation()
            updateWord()
            binding.englishTranslation.text.clear()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer() // Timer'ı duraklat
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetGame() // Oyunu sıfırla ve timer'ı başlat
    }

    private fun updateWord() {
        val currentWord = viewModel.getCurrentWord()
        binding.turkishWord.text = currentWord?.translation ?: "Kelime Yükleniyor..."
    }

    private fun onTimeUp() {
        binding.submitButton.isEnabled = false
        AlertDialog.Builder(requireContext())
            .setTitle("Oyun Bitti!")
            .setMessage("Skorunuz: Doğru: ${viewModel.correctCount.value}\nYanlış: ${viewModel.incorrectCount.value}\nTekrar oynamak ister misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                viewModel.resetGame() // Oyunu sıfırla
                binding.submitButton.isEnabled = true
            }
            .setNegativeButton("Hayır") { _, _ ->
                findNavController().navigate(R.id.action_wordGameFragment_to_homeFragment) // Ana ekrana dön
            }
            .show()
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }

    @SuppressLint("ResourceType")
    fun shakeView(view: View) {
        val shake = AnimationUtils.loadAnimation(requireContext(), R.drawable.shake)
        view.startAnimation(shake)
    }
}

