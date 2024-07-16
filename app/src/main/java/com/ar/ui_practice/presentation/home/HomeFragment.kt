package com.ar.ui_practice.presentation.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.R.*
import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.adapter.ShortCutAdapter
import com.ar.ui_practice.bottomSheet.ShortCutSelector
import com.ar.ui_practice.data.demo.DemoData.shortcutData
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.databinding.FragmentHomeBinding

class HomeFragment : Fragment(layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ShortCutAdapter
    private var isRemoveShow = false
    private var isBalanceShow = false
    private var listener : HomeListener? = null

    private val initList =
        mutableListOf(
            ShortcutData(0, ic_add, "Shortcut"),
            ShortcutData(1, ic_add, "Shortcut"),
            ShortcutData(2, ic_add, "Shortcut"),
            ShortcutData(3, ic_add, "Shortcut"),
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HomeListener){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private val shortcutList = shortcutData.toMutableList()

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

    private fun initBasic() {
        binding.profile.tvBalance.text = "00,00,000"
        binding.profile.tvName.text = "Anonymous User"
        binding.profile.tvUserTag.visibility = View.VISIBLE
        binding.profile.tvUserTag.text = "Basic"
        binding.profile.llRegistration.visibility = View.VISIBLE
        binding.profile.llExplore.visibility = View.VISIBLE
        binding.profile.tvRegistration.visibility = View.VISIBLE
        binding.profile.btnRegisterNow.visibility = View.VISIBLE
        binding.profile.tvExplore.visibility = View.VISIBLE
        binding.profile.btnExplore.visibility = View.VISIBLE
        binding.profile.llTransactions.visibility = View.GONE
        initBasicItems()
        initListener()
    }

    private fun initBasicItems() {
        adapter = ShortCutAdapter( onClick = { data ->
            binding.root.performClick()
        }, onLongClick = {

        })
        binding.profile.rvShortcuts.apply {
            layoutManager = GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
        binding.profile.rvShortcuts.adapter = adapter
        adapter.submitList(initList)
    }

    private fun initItems() {
        adapter = ShortCutAdapter( onClick = { data ->
            if (data.title == "Shortcut") {
                val shortCutSelector = ShortCutSelector(
                    shortcutData = shortcutList.toList(),
                    onClick = { selectedData ->
                        shortcutList.remove(selectedData)
                        initList[data.id] = ShortcutData(data.id, selectedData.icon, selectedData.title, false)
                        isRemoveShow = false
                        changeState()
                        adapter.notifyItemChanged(data.id)
                    }
                )
                shortCutSelector.show(requireActivity().supportFragmentManager, "ShortCutSelector")
            }else{
                if(data.removeIcon){
                    initList[data.id] = ShortcutData(data.id, ic_add, "Shortcut")
                    adapter.notifyItemChanged(data.id)
                    val item = shortcutData.find{
                        data.title == it.title
                    }
                    shortcutList.add(item!!)
                    shortcutList.sortBy { it.id }
                }
            }
        }, onLongClick = {
            isRemoveShow = !isRemoveShow
            initList.forEach {
                if(it.title != "Shortcut"){
                    it.removeIcon = !it.removeIcon
                    adapter.notifyItemChanged(it.id)
                }
            }
        })
        binding.profile.rvShortcuts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 4, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
        binding.profile.rvShortcuts.adapter = adapter
        adapter.submitList(initList)
    }

    private fun changeState() {
        initList.forEach {
            if(it.title != "Shortcut"){
                it.removeIcon = false
                adapter.notifyItemChanged(it.id)
            }
        }
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            binding.basicPopup.root.visibility = View.VISIBLE
        }
        binding.basicPopup.closeDialog.setOnClickListener {
            binding.basicPopup.root.visibility = View.GONE
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
        binding.profile.llRegistration.visibility = View.GONE
        binding.profile.llExplore.visibility = View.GONE
        binding.profile.tvRegistration.visibility = View.GONE
        binding.profile.btnRegisterNow.visibility = View.GONE
        binding.profile.tvExplore.visibility = View.GONE
        binding.profile.btnExplore.visibility = View.GONE
        binding.profile.tvUserTag.visibility = View.GONE
        binding.profile.llTransactions.visibility = View.VISIBLE
        initItems()
        initMainListener()
    }

    private fun initMainListener() {
        binding.profile.profileImage.setOnClickListener {
            listener?.onDrawerClick()
        }
        binding.root.setOnClickListener {
            if(isRemoveShow){
                isRemoveShow = false
                changeState()
            }
        }
        binding.profile.balanceToogle.setOnClickListener{
            if(isBalanceShow){
                isBalanceShow = false
                binding.profile.tvBalance.text = "********"
                binding.profile.balanceToogle.setImageResource(ic_hide_balance)
            }else{
                isBalanceShow = true
                binding.profile.tvBalance.text = "4,00,000"
                binding.profile.balanceToogle.setImageResource(ic_show_balance)
            }
        }
    }
}