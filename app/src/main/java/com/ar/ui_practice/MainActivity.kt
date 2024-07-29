package com.ar.ui_practice

import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ar.ui_practice.R.drawable.*
import com.ar.ui_practice.R.string.*
import com.ar.ui_practice.databinding.ActivityMainBinding
import com.ar.ui_practice.presentation.home.HomeListener
import com.ar.ui_practice.presentation.top_up.fragment.OfferListFragment

class MainActivity : AppCompatActivity(), HomeListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<MarginLayoutParams> {
                leftMargin = systemBars.left
                rightMargin = systemBars.right
            }
            insets
        }
        if(!allPermissionsGranted()){
            requestPermissions()
        }
        initListener()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomBar.bottomNavigationView.setupWithNavController(navController)

        binding.bottomBar.bottomNavigationView.menu.findItem(R.id.chat).setIcon(icon_chats_no_notification)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment ->{
                    binding.bottomBar.root.visibility = View.VISIBLE
                }
                R.id.servicesFragment->{
                    binding.bottomBar.root.visibility = View.VISIBLE
                }
                else ->{
                    binding.bottomBar.root.visibility = View.GONE
                }
            }
        }
    }

    private fun initListener() {
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
    override fun onDrawerClick() {
        binding.main.open()
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }
    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in REQUIRED_PERMISSIONS && !it.value)
                permissionGranted = false
        }
        if (!permissionGranted) {
            Toast.makeText(baseContext, "Permission request denied", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        private val REQUIRED_PERMISSIONS = mutableListOf(
            READ_CONTACTS
        ).toTypedArray()
    }
}
