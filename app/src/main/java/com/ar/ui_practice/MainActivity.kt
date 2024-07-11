package com.ar.ui_practice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.R.string.*
import com.ar.ui_practice.adapter.ShortCutAdapter
import com.ar.ui_practice.bottomSheet.ShortCutSelector
import com.ar.ui_practice.data.demo.DemoData.shortcutData
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShortCutAdapter

    private val initList =
        mutableListOf(
            ShortcutData(0,ic_add, "Shortcut"),
            ShortcutData(1,ic_add, "Shortcut"),
            ShortcutData(2, ic_add, "Shortcut"),
            ShortcutData(3, ic_add, "Shortcut"),
        )

    private val shortcutList = shortcutData.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // binding.bottomBar.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.content.bottomBar.bottomNavigationView.itemIconTintList = null
        binding.content.basicPopup.registerNowLayout
            .bringChildToFront(binding.content.basicPopup.registerNowDialog)
        tossUser()
    }

    private fun tossUser() {
        // val user = 1
        val user = 2
        if (user % 2 == 0) {
            initInfo()
        } else {
            initBasic()
        }
    }

    private fun initBasic() {
        binding.content.profile.tvBalance.text = "00,00,000"
        binding.content.profile.tvName.text = "Guest User"
        binding.content.profile.tvUserTag.visibility = View.VISIBLE
        binding.content.profile.tvUserTag.text = "Basic"
        binding.content.profile.llRegistration.visibility = View.VISIBLE
        binding.content.profile.llExplore.visibility = View.VISIBLE
        binding.content.profile.tvRegistration.visibility = View.VISIBLE
        binding.content.profile.btnRegisterNow.visibility = View.VISIBLE
        binding.content.profile.tvExplore.visibility = View.VISIBLE
        binding.content.profile.btnExplore.visibility = View.VISIBLE
        binding.content.profile.llTransactions.visibility = View.GONE
        initItems()
        initListener()
    }

    private fun initItems() {
        adapter = ShortCutAdapter( onClick = { data ->
            if (data.title == "Shortcut") {
                val shortCutSelector = ShortCutSelector(
                    shortcutData = shortcutList.toList(),
                    onClick = { selectedData ->
                        shortcutList.remove(selectedData)
                        initList[data.id] = ShortcutData(data.id, selectedData.icon, selectedData.title, false)
                        adapter.notifyItemChanged(data.id)
                    }
                )
                shortCutSelector.show(supportFragmentManager, "ShortCutSelector")
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
            initList.forEach {
                if(it.title != "Shortcut"){
                    it.removeIcon = !it.removeIcon
                    adapter.notifyItemChanged(it.id)
                }
            }
        })
        binding.content.profile.rvShortcuts.apply {
            layoutManager =
                GridLayoutManager(this@MainActivity, 4, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
        }
        binding.content.profile.rvShortcuts.adapter = adapter
        adapter.submitList(initList)
    }

    private fun initListener() {
        binding.root.setOnClickListener {
            binding.content.basicPopup.root.visibility = View.VISIBLE
        }
        binding.content.basicPopup.closeDialog.setOnClickListener {
            binding.content.basicPopup.root.visibility = View.GONE
        }
        binding.content.basicPopup.btnRegisterNow1.setOnClickListener {
            binding.content.basicPopup.root
                .performClick()
        }
        binding.content.profile.btnRegisterNow.setOnClickListener {
            binding.root.performClick()
        }
        binding.content.profile.btnExplore.setOnClickListener {
            binding.root.performClick()
        }
        binding.content.bottomBar.fabScanQr.setOnClickListener {
            binding.root.performClick()
        }
        binding.content.bottomBar.bottomNavigationView.setOnItemSelectedListener {
            binding.root.performClick()
        }
    }

    private fun initInfo() {
        binding.content.profile.tvBalance.text = "4,00,000"
        binding.content.profile.tvName.text = "Maruf Ahmed"
        binding.content.profile.llRegistration.visibility = View.GONE
        binding.content.profile.llExplore.visibility = View.GONE
        binding.content.profile.tvRegistration.visibility = View.GONE
        binding.content.profile.btnRegisterNow.visibility = View.GONE
        binding.content.profile.tvExplore.visibility = View.GONE
        binding.content.profile.btnExplore.visibility = View.GONE
        binding.content.profile.tvUserTag.visibility = View.GONE
        binding.content.profile.llTransactions.visibility = View.VISIBLE
        initItems()
        initMainListener()
    }

    private fun initMainListener() {
        binding.content.profile.profileImage.setOnClickListener {
            binding.main.open()
        }
        setAllOptions()
    }

    private fun setAllOptions() {
        binding.drawer.header.apply {
            name.text = "Maruf Ahmed"
        }
        binding.drawer.apply {
            drawerOptionQr.optionName.text = getString(my_qr)
            drawerOptionLang.optionName.text = getString(change_language)
            drawerOptionTheme.optionName.text = getString(appearance)
            drawerOptionBiometric.optionName.text = getString(biometric_unlock)
            drawerOptionScut.optionName.text = getString(floating_shortcut)
            drawerOptionBankAcc.optionName.text = getString(bank_accounts)
            drawerOptionAccSetting.optionName.text = getString(account_settings)
            drawerOptionFeedback.optionName.text = getString(feedback)
            drawerOptionSupport.optionName.text = getString(_24x7_support)
            drawerOptionRefer.optionName.text = getString(R.string.refer)
            drawerOptionLimit.optionName.text = getString(limit_usage)

            drawerOptionQr.icon.setImageResource(ic_qr_2)
            drawerOptionLang.icon.setImageResource(ic_lang)
            drawerOptionTheme.icon.setImageResource(ic_theme)
            drawerOptionBiometric.icon.setImageResource(ic_biometric)
            drawerOptionScut.icon.setImageResource(ic_fab_scut)
            drawerOptionBankAcc.icon.setImageResource(ic_bank_acc)
            drawerOptionAccSetting.icon.setImageResource(ic_acc_setting)
            drawerOptionFeedback.icon.setImageResource(ic_feedback)
            drawerOptionSupport.icon.setImageResource(ic_support)
            drawerOptionRefer.icon.setImageResource(R.drawable.refer)
            drawerOptionLimit.icon.setImageResource(ic_limit)
        }
    }
}
