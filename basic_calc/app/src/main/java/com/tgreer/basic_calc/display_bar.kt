package com.tgreer.basic_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tgreer.basic_calc.databinding.FragmentDisplayBarBinding

class display_bar : Fragment(){
    private lateinit var binding : FragmentDisplayBarBinding

    interface display_barListener{
        fun onButtonCLick(text: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setString(value: String){
        binding.topFragText.text = value
    }

}