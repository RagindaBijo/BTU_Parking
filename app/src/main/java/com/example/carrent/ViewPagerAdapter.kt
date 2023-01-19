package com.example.carrent

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carrent.InnerFragment.InnerFragmentFirst
import com.example.carrent.InnerFragment.InnerFragmentSecond

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 ->{InnerFragmentFirst()}
            1 ->{InnerFragmentSecond()}
            else->{throw NotFoundException("ხარვეზი") }
        }
    }

}