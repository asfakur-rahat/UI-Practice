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
            layoutManager = GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
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