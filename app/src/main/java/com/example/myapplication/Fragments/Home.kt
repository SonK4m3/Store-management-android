package com.example.myapplication.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.HomeLayoutBinding

class Home: Fragment() {

    companion object {
        const val CUSTOMER_PAGE_ID = 2
    }

    private var binding: HomeLayoutBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeLayoutBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.changeToCustomer?.setOnClickListener(){
            val viewPager = (activity as MainActivity).getViewPager()
            viewPager.setCurrentItem(CUSTOMER_PAGE_ID, true)
            Log.d("AAA","go to customer")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}