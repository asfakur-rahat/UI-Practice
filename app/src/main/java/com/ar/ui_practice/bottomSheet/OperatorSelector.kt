package com.ar.ui_practice.bottomSheet

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.contacts.OperatorSelectorAdapter
import com.ar.ui_practice.data.model.MobileOperator
import com.ar.ui_practice.databinding.OperatorSelectionBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OperatorSelector (
    private val onClick: (String, String?) -> Unit,
    private val operatorData: List<MobileOperator>,
) : BottomSheetDialogFragment(R.layout.operator_selection_bottom_sheet) {
    private lateinit var binding: OperatorSelectionBottomSheetBinding
    private lateinit var adapter: OperatorSelectorAdapter

    private var selectedOperator: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = OperatorSelectionBottomSheetBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initItems()
    }

    private fun initItems(){
        binding.prepaid.visibility = View.GONE
        binding.postpaid.visibility = View.GONE
        adapter = OperatorSelectorAdapter{ operator ->
            selectedOperator = operator.title
            if(operator.title != "Skitto"){
                binding.prepaid.visibility = View.VISIBLE
                binding.postpaid.visibility = View.VISIBLE
            }else{
                binding.prepaid.visibility = View.GONE
                binding.postpaid.visibility = View.GONE
                onClick(operator.title, null)
                dismiss()
            }
        }
        binding.rvOperators.layoutManager = GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
        binding.rvOperators.adapter = adapter
        adapter.submitList(operatorData)


        binding.prepaid.setOnClickListener {
            onClick(selectedOperator!!, "Prepaid")
            dismiss()
        }
        binding.postpaid.setOnClickListener {
            onClick(selectedOperator!!, "Postpaid")
            dismiss()
        }
    }
}