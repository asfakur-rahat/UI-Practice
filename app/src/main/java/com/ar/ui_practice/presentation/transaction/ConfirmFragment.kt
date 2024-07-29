package com.ar.ui_practice.presentation.transaction

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.adapter.transaction.PinAdapter
import com.ar.ui_practice.databinding.FragmentConfirmBinding

class ConfirmFragment : Fragment() {
    private var _binding : FragmentConfirmBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PinAdapter


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

    private fun initWatcher() {
        binding.etPIN.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val len = s?.length ?: 0
                val list = mutableListOf<Int>()
                for(i in 0..<len){
                    list.add(i)
                }
                setUpPIN(list)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setUpPIN(list: MutableList<Int>) {
        adapter = PinAdapter()
        binding.rvPinPlaceholder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        binding.rvPinPlaceholder.adapter = adapter
        println("User Enter something")
        adapter.submitList(list)
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = "Mobile Recharge"
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener { 
            findNavController().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}