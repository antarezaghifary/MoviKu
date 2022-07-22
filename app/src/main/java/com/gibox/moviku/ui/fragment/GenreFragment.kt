package com.gibox.moviku.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gibox.moviku.databinding.FragmentGenreBinding

class GenreFragment : Fragment() {

    private val binding by lazy {
        FragmentGenreBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}