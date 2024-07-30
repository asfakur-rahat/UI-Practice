package com.ar.ui_practice.presentation.contacts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.contacts.AllContactsAdapter
import com.ar.ui_practice.bottomSheet.OperatorSelector
import com.ar.ui_practice.data.demo.DemoData
import com.ar.ui_practice.data.model.Contacts
import com.ar.ui_practice.databinding.FragmentContactsBinding

class ContactsFragment : Fragment(R.layout.fragment_contacts){

    private lateinit var binding: FragmentContactsBinding
    private lateinit var adapter: AllContactsAdapter
    private lateinit var adapter2: AllContactsAdapter
    private val args: ContactsFragmentArgs by navArgs()
    private val viewModel : ContactsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentContactsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        adapter = AllContactsAdapter{
            selectOperator(it.name, it.number)
        }
        adapter2 = AllContactsAdapter{
            selectOperator(it.name, it.number)
        }
        initView()
        initListener()
    }

    private fun selectOperator(name: String = "Unknown", number: String) {
        val operatorSelector = OperatorSelector({ _, _ ->
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToTopUpFragment(name = name, number = number, title = args.title))
        }, operatorData = DemoData.operatorList)

        operatorSelector.show(requireActivity().supportFragmentManager, "OperatorSelector")
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = args.title
        viewModel.loadContacts(requireContext())
        initObserver()
        initTest()
    }

    private fun initObserver() {
        viewModel.allContactList.observe(viewLifecycleOwner){
            if(it != null){
                setupRecyclerView(it)
            }
        }
        viewModel.recentContactList.observe(viewLifecycleOwner){
            if (it != null){
                setUpRecent(it)
            }
        }
    }

    private fun initTest() {
        binding.etContact.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()){
                    binding.confirmBtn.setImageResource(R.drawable.ic_action_btn)
                    binding.confirmBtn.setOnClickListener {

                    }
                }else{
                    binding.confirmBtn.setImageResource(R.drawable.ic_action_bth_active)
                    if (s.length == 11){
                        binding.confirmBtn.setOnClickListener {
                            selectOperator(number = s.toString())
                        }
                    }else{
                        binding.confirmBtn.setOnClickListener {

                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun setUpRecent(recentContacts: MutableList<Contacts>) {
        binding.rvRecentContactsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRecentContactsList.adapter = adapter2
        adapter2.submitList(recentContacts)
    }

    private fun setupRecyclerView(contacts: MutableList<Contacts>) {
        binding.rvAllContactsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAllContactsList.adapter = adapter
        adapter.submitList(contacts)
    }
}