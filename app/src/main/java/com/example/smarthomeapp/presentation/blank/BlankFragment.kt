package com.example.smarthomeapp.presentation.blank

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.example.smarthomeapp.R
import com.example.smarthomeapp.util.extensions.ResourceXs

class BlankFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = AppCompatTextView(inflater.context).apply {
        arguments?.let {
            text = it.getString(Intent.EXTRA_KEY_EVENT)
            setTextColor(Color.BLACK)
            textSize = ResourceXs.getDimensionPixelSize(R.dimen.font_xmedium).toFloat()
        }
    }

    companion object {
        fun newInstance(text: String) = BlankFragment().apply {
            arguments = Bundle().apply {
                putString(Intent.EXTRA_KEY_EVENT, text)
            }
        }
    }
}