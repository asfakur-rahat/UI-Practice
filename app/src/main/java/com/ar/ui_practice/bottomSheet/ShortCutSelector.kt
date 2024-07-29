package com.ar.ui_practice.bottomSheet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.home.ShortCutSelectorAdapter
import com.ar.ui_practice.data.model.ShortcutData
import com.ar.ui_practice.databinding.ShortCutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShortCutSelector(
    private val onClick: (ShortcutData) -> Unit,
    private val shortcutData: List<ShortcutData>,
) : BottomSheetDialogFragment(R.layout.short_cut_bottom_sheet) {
    private lateinit var binding: ShortCutBottomSheetBinding
    private lateinit var adapter: ShortCutSelectorAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ShortCutBottomSheetBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initItems()
    }

    private fun initItems(){
        adapter = ShortCutSelectorAdapter{ data ->
            onClick(data)
            dismiss()
        }
        binding.rvShortcutsAll.layoutManager = GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
        binding.rvShortcutsAll.adapter = adapter
        adapter.submitList(shortcutData)
    }
}