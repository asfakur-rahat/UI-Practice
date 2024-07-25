package com.ar.ui_practice.adapter.top_up

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ar.ui_practice.data.model.Offer
import com.ar.ui_practice.presentation.top_up.fragment.OfferViewPage

class ViewPagerAdapter(
    fragment: Fragment,
    private val itemsList: List<List<Offer>>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return OfferViewPage.newInstance(itemsList[position])
    }
}
