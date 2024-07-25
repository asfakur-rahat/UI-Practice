package com.ar.ui_practice.presentation.top_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.top_up.OfferViewPagerAdapter
import com.ar.ui_practice.adapter.top_up.OffersAdapter
import com.ar.ui_practice.data.demo.DemoData
import com.ar.ui_practice.databinding.FragmentTopUpBinding


class TopUpFragment : Fragment() {
    private var _binding: FragmentTopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OffersAdapter
    private lateinit var viewPager: ViewPager2


    private val offers = DemoData.OffersList


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.offers.apply {
            adapter = OffersAdapter { position ->
                viewPager.setCurrentItem(position, true)
            }
            rvTitle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTitle.adapter = adapter
            adapter.submitList(offers.map { it.category })

            val viewPageAdapter = OfferViewPagerAdapter(this@TopUpFragment, offers)

            viewPager = horizontalViewPager
            horizontalViewPager.adapter = viewPageAdapter

            horizontalViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    adapter.updateSelectedPosition(position)
                    rvTitle.scrollToPosition(position)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}