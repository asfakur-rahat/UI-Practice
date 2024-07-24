package com.ar.ui_practice.adapter.top_up

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ar.ui_practice.data.model.top_up.Offers
import com.ar.ui_practice.presentation.top_up.fragment.OfferListFragment

class OfferViewPagerAdapter(
    fragment: Fragment,
    private val offersList: List<Offers>
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return offersList.size
    }

    override fun createFragment(position: Int): Fragment {
        return OfferListFragment.newInstance(offersList[position].offerList)
    }
}