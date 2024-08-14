package com.ar.ui_practice.presentation.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.R.*
import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.adapter.home.ShortCutAdapter
import com.ar.ui_practice.bottomSheet.ShortCutSelector
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.databinding.FragmentHomeBinding
import com.ar.ui_practice.utils.setVisibility
import com.ar.ui_practice.dialog.PromotionDialog
import com.ar.ui_practice.ui_component.carousel.model.CarouselItem

class HomeFragment : Fragment(layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ShortCutAdapter
    private var isRemoveShow = false
    private var isBalanceShow = false
    private var listener: HomeListener? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private var initList  = mutableListOf<ShortcutData>()
    private var shortcutList = mutableListOf<ShortcutData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        tossUser()
    }

    private fun tossUser() {
        val user = 2//Random.nextInt(1, 4)
        if (user % 2 == 0) {
            initInfo()
        } else {
            initBasic()
        }
    }

    private fun initObserver() {
        viewModel.shorCutList.observe(viewLifecycleOwner){
            initList = it
            initShortcut(it)
        }
        viewModel.shortCutSelectionList.observe(viewLifecycleOwner){
            shortcutList = it
        }
    }

    private fun initShortcut(list: MutableList<ShortcutData>) {

        adapter = ShortCutAdapter(onClick = { data ->
            if (data.title == "Shortcut") {
                val shortCutSelector = ShortCutSelector(
                    shortcutData = shortcutList.toList(),
                    onClick = { selectedData ->
                        isRemoveShow = false
                        viewModel.updateShortcut(data, selectedData)
                    }
                )
                shortCutSelector.show(requireActivity().supportFragmentManager, "ShortCutSelector")
            } else {
                if (data.removeIcon) {
                    viewModel.removeShortCut(data)
                }
            }
        }, onLongClick = {
            isRemoveShow = !isRemoveShow
            viewModel.updateRemoveShow()
        })


        binding.profile.rvShortcuts.apply {
            layoutManager = GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
        binding.profile.rvShortcuts.adapter = adapter
        adapter.submitList(list)
    }

    private fun initBasic() {
        binding.profile.tvBalance.text = "00,00,000"
        binding.profile.tvName.text = "Anonymous User"
        binding.profile.tvUserTag.visibility = View.VISIBLE
        binding.profile.tvUserTag.text = "Basic"
        binding.profile.tvUserTag.setVisibility(true)
        binding.profile.llRegistration.setVisibility(true)
        binding.profile.llExplore.setVisibility(true)
        binding.profile.tvRegistration.setVisibility(true)
        binding.profile.btnRegisterNow.setVisibility(true)
        binding.profile.tvExplore.setVisibility(true)
        binding.profile.btnExplore.setVisibility(true)
        binding.profile.llTransactions.setVisibility(false)
        initBasicItems()
        initListener()
    }

    private fun initBasicItems() {
        adapter = ShortCutAdapter(onClick = {
            binding.root.performClick()
        }, onLongClick = {

        })
        binding.profile.rvShortcuts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
        binding.profile.rvShortcuts.adapter = adapter
        //adapter.submitList(initList)
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            binding.basicPopup.root.setVisibility(true)
        }
        binding.basicPopup.closeDialog.setOnClickListener {
            binding.basicPopup.root.setVisibility(false)
        }
        binding.basicPopup.btnRegisterNow1.setOnClickListener {
            binding.basicPopup.root.performClick()
        }
        binding.profile.btnRegisterNow.setOnClickListener {
            binding.root.performClick()
        }
        binding.profile.btnExplore.setOnClickListener {
            binding.root.performClick()
        }
//        binding.bottomBar.fabScanQr.setOnClickListener {
//            binding.root.performClick()
//        }
//        binding.bottomBar.bottomNavigationView.setOnItemSelectedListener {
//            binding.root.performClick()
//        }
    }

    private fun initInfo() {
        binding.profile.tvBalance.text = "4,00,000"
        binding.profile.tvName.text = "Maruf Ahmed"
        binding.profile.llRegistration.setVisibility(false)
        binding.profile.llExplore.setVisibility(false)
        binding.profile.tvRegistration.setVisibility(false)
        binding.profile.btnRegisterNow.setVisibility(false)
        binding.profile.tvExplore.setVisibility(false)
        binding.profile.btnExplore.setVisibility(false)
        binding.profile.tvUserTag.setVisibility(false)
        binding.profile.llTransactions.setVisibility(true)
        //initItems()
        initObserver()
        initMainListener()

        initDialog()
    }

    private fun initDialog() {
        val dialog = PromotionDialog(
            requireContext(), this, onClick = {

            }, items = listOf(
                CarouselItem(
                    imageUrl = "https://ecdn.dhakatribune.net/contents/cache/images/1100x618x1/uploads/dten/2022/06/20/free-talk-time-sylhet.jpeg",
                    caption = "Celebrating Bangladesh's 50th Independence Day with Pride \uD83C\uDDE7\uD83C\uDDE9",
                    subtitle = "This vibrant graphic marks the 50th anniversary of Bangladesh's independence. The design features bold typography and the iconic red and green colors of the national flag, symbolizing the nation's enduring spirit and resilience. The message, '50 Years of Freedom,' highlights the journey of progress and unity since 1971."
                ),
                CarouselItem(
                    imageUrl = "https://coorzjdvba.cloudimg.io/markedium.com/wp-content/uploads/2021/11/Copy-of-Add-a-subheading-1080-x-1350-px51.png",
                    caption = "Exclusive GP Star Discount on Truck Hiring with Truck Lagbe \uD83D\uDE9A",
                    subtitle = "This promotional image showcases a special offer for GP Star customers, providing discounts on truck hiring services through Truck Lagbe. The visual highlights the convenience and affordability of transporting goods across the country, featuring the Truck Lagbe logo alongside the GP Star branding."
                ),
                CarouselItem(
                    imageUrl = "https://blog.trucklagbe.com/hubfs/GP%20Star%20Discount%20on%20truck%20hiring%20from%20Truck%20Lagbe.jpg",
                    caption = "Stay Connected with GP's Exclusive Mobile Plans \uD83D\uDCF1",
                    subtitle = "This image highlights Grameenphone's latest mobile plans, designed to keep users connected with high-speed data and affordable rates. The sleek and modern design features a smartphone alongside the Grameenphone logo, symbolizing the brand's commitment to providing cutting-edge technology and exceptional service."
                ),
                CarouselItem(
                    imageUrl = "https://blog.trucklagbe.com/hubfs/GP%20Star%20Discount%20on%20truck%20hiring%20from%20Truck%20Lagbe.jpg",
                    caption = "Stay Connected with GP's Exclusive Mobile Plans \uD83D\uDCF1",
                    subtitle = "This image highlights Grameenphone's latest mobile plans, designed to keep users connected with high-speed data and affordable rates. The sleek and modern design features a smartphone alongside the Grameenphone logo, symbolizing the brand's commitment to providing cutting-edge technology and exceptional service."
                ),
            )
        )

        dialog.show()
    }

    private fun initMainListener() {
        binding.profile.profileImage.setOnClickListener {
            listener?.onDrawerClick()
        }
        binding.root.setOnClickListener {
            if (isRemoveShow) {
                isRemoveShow = false
                viewModel.changeState()
            }
        }
        binding.profile.balanceToogle.setOnClickListener {
            if (isBalanceShow) {
                isBalanceShow = false
                binding.profile.tvBalance.text = "********"
                binding.profile.balanceToogle.setImageResource(ic_hide_balance)
            } else {
                isBalanceShow = true
                binding.profile.tvBalance.text = "4,00,000"
                binding.profile.balanceToogle.setImageResource(ic_show_balance)
            }
        }
    }
}