package com.ar.ui_practice.presentation.transaction

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.adapter.transaction.PinAdapter
import com.ar.ui_practice.databinding.FragmentConfirmBinding
import com.ar.ui_practice.utils.setVisibility
import com.google.android.material.internal.ViewUtils.hideKeyboard

class ConfirmFragment : Fragment() {
    private var _binding : FragmentConfirmBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PinAdapter

    private val args: ConfirmFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initWatcher()
    }

    @SuppressLint("RestrictedApi")
    private fun initWatcher() {
        binding.etPIN.doOnTextChanged { s, _, _, _ ->
            val len = s?.length ?: 0
            binding.pinPlaceholder.setVisibility(len == 0)
            if (len <= 4){
                val list = mutableListOf<Int>()
                for(i in 0..<len){
                    list.add(i)
                }
                setUpPIN(list)
                if (len == 4){
                    hideKeyboard(binding.root)
                    binding.etPIN.clearFocus()
                }
            }
        }
    }

    private fun setUpPIN(list: MutableList<Int>) {
        adapter = PinAdapter()
        binding.rvPinPlaceholder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.rvPinPlaceholder.adapter = adapter
        adapter.submitList(list)
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = "Mobile Recharge"
        binding.contactName.text = args.name
        binding.contactNumber.text = args.number
        binding.tvAmount.text = args.amount
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener { 
            findNavController().popBackStack()
        }
        binding.slider.onSlideDoneListener ={
            if(binding.etPIN.text.toString().trimMargin() == "1111"){
                gotoSuccess()
            }else{
                binding.slider.resetSlider()
                gotoFailure()
            }
        }
    }

    private fun gotoFailure() {
        Toast.makeText(requireContext(), "Please Enter the correct PIN", Toast.LENGTH_SHORT).show()
    }

    private fun gotoSuccess() {
        findNavController().navigate(ConfirmFragmentDirections.actionConfirmFragmentToSuccessFragment(name = args.name, number = args.number, amount = args.amount, charge = binding.tvCharge.text.toString()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}