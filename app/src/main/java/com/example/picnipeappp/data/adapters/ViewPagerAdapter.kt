package com.example.picnipeappp.data.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.picnipeappp.ui.components.UploadsFragment


class ViewPagerAdapter(fm: Fragment): FragmentStateAdapter(fm) {

    private val fragments = ArrayList<Fragment>()
    private val fragmentsTitles = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment)
        fragmentsTitles.add(title)
    }

    fun getFragmentTitle(position: Int): String{
        return fragmentsTitles[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragments[position]
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt("object", position + 1)
        }
        return fragment
    }

}