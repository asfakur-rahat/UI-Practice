package com.ar.ui_practice

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.ar.ui_practice.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //binding.bottomBar.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomBar.bottomNavigationView.itemIconTintList = null
        binding.basicPopup.registerNowLayout.bringChildToFront(binding.basicPopup.registerNowDialog)
        tossUser()
    }

    private fun tossUser() {
        //val user = 1
        val user = 2
        if (user % 2 == 0) {
            initInfo()
        }else{
            initBasic()
        }
    }

    private fun initBasic() {
        binding.profile.tvBalance.text = "00,00,000"
        binding.profile.tvName.text = "Guest User"
        binding.profile.tvUserTag.visibility = View.VISIBLE
        binding.profile.tvUserTag.text = "Basic"
        binding.profile.llRegistration.visibility = View.VISIBLE
        binding.profile.llExplore.visibility = View.VISIBLE
        binding.profile.tvRegistration.visibility = View.VISIBLE
        binding.profile.btnRegisterNow.visibility = View.VISIBLE
        binding.profile.tvExplore.visibility = View.VISIBLE
        binding.profile.btnExplore.visibility = View.VISIBLE
        binding.profile.llTransactions.visibility = View.GONE
        initListener()
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
        binding.bottomBar.fabScanQr.setOnClickListener {
            binding.root.performClick()
        }
        binding.bottomBar.bottomNavigationView.setOnItemSelectedListener {
            binding.root.performClick()
        }
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
    }
}