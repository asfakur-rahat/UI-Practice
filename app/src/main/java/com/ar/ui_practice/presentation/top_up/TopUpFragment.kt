package com.ar.ui_practice.presentation.top_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.top_up.OptionAdapter
import com.ar.ui_practice.adapter.top_up.ViewPagerAdapter
import com.ar.ui_practice.data.demo.DemoData.Options
import com.ar.ui_practice.data.demo.DemoData.Offers
import com.ar.ui_practice.databinding.FragmentTopUpBinding


class TopUpFragment : Fragment() {
    private var _binding: FragmentTopUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var horizontalAdapter: OptionAdapter
    private lateinit var viewPager2: ViewPager2

    private val horizontalItems = Options
    private val verticalItemsList = listOf(
        Offers,
        Offers,
        Offers,
        Offers,
        Offers
    )


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
            horizontalAdapter = OptionAdapter{ position ->
                viewPager2.setCurrentItem(position, true)
            }
            rvTitle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTitle.adapter = horizontalAdapter
            horizontalAdapter.submitList(horizontalItems)

            val viewPagerAdapter = ViewPagerAdapter(this@TopUpFragment, verticalItemsList)
            viewPager2 = horizontalViewpager
            horizontalViewpager.adapter = viewPagerAdapter

            horizontalViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    horizontalAdapter.updateSelectedPosition(position)
                    rvTitle.scrollToPosition(position)
                }
            })
        }
        binding.topBar.apply {
            tvNavTitle.text = "Mobile Recharge"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}