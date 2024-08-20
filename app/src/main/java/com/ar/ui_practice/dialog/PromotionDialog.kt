package com.ar.ui_practice.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import com.ar.ui_practice.R
import com.ar.ui_practice.databinding.PromotionDialogBinding
import com.ar.ui_practice.ui_component.carousel.listener.CarouselListener
import com.ar.ui_practice.ui_component.carousel.model.CarouselItem

class PromotionDialog(
    context: Context,
    private val parent: Fragment,
    private val onClick: () -> Unit,
    private val items: List<CarouselItem>
): Dialog(context, R.style.TransparentDialog) {

    private lateinit var binding: PromotionDialogBinding

    override fun onStart() {
        super.onStart()
        initBounds()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PromotionDialogBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        initView()
        initListener()
    }

    private fun initBounds() {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private fun initListener() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun initView() {
        val carousel = binding.carousel

        carousel.registerLifecycle(parent.viewLifecycleOwner)

        carousel.carouselListener = object : CarouselListener{
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                super.onClick(position, carouselItem)
                onClick()
            }
        }

        carousel.setData(items)
    }
}