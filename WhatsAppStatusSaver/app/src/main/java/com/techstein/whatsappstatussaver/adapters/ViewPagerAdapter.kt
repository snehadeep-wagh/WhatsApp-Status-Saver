package com.techstein.whatsappstatussaver.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.techstein.whatsappstatussaver.fragments.DownloadsFragment
import com.techstein.whatsappstatussaver.fragments.ImageFragment
import com.techstein.whatsappstatussaver.fragments.VideoFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0 -> ImageFragment()
            1 -> VideoFragment()
            else -> DownloadsFragment()
        }
    }

}