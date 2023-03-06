package com.example.myapplication.ViewModels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.myapplication.Fragments.Bill
import com.example.myapplication.Fragments.Customer
import com.example.myapplication.Fragments.Home

class MainAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        const val FRAGMENT_NUMBER = 3
    }

    private lateinit var title: String

    override fun getItemCount(): Int = FRAGMENT_NUMBER

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                Home()
            }
            1 -> {
                Bill()
            }
            2 -> {
                Customer()
            }
            else -> { Home() }
        }
    }
}