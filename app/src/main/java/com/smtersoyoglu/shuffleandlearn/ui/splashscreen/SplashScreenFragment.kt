package com.smtersoyoglu.shuffleandlearn.ui.splashscreen

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.smtersoyoglu.shuffleandlearn.R


class SplashScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timer = object: CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }
        }
        timer.start()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
    
}