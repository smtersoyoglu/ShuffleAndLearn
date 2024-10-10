package com.smtersoyoglu.shuffleandlearn

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.smtersoyoglu.shuffleandlearn.databinding.FragmentPhotoDialogBinding


class PhotoDialogFragment(private val imageResId: Int) : DialogFragment() {

    private var _binding : FragmentPhotoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDialogBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Görseli ayarlamak
        binding.fullscreenImage.setImageResource(imageResId)

        // Kapatmak için tıklama
        binding.fullscreenImage.setOnClickListener {
            dismiss() // Dialogu kapat
        }
    }

    override fun onStart() {
        super.onStart()
        // Dialogun istedigimiz boyutta olmasını sağlar
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),  // Ekran genişliğinin %80'i
            (resources.displayMetrics.widthPixels * 0.8).toInt()   // Ekran genişliğinin %80'i (yuvarlak bir çerçeve sağlamak için kare boyut)
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}