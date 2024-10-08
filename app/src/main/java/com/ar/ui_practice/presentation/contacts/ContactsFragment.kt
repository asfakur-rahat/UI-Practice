package com.ar.ui_practice.presentation.contacts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
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
import com.ar.ui_practice.utils.setVisibility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        val operatorSelector = OperatorSelector(requireContext(),{ _, _ ->
            gotoTopUp(name,number,args.title)
        }, operatorData = DemoData.operatorList)
        operatorSelector.show()
    }

    private fun gotoTopUp(name: String, number: String, title: String) {
        findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToTopUpFragment(name = name, number = number, title = title))
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = args.title
        binding.confirmBtn.isEnabled = false
        viewModel.loadContacts(requireContext())
        initObserver()
        doOnTextChange()
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


    private var debounceJob: Job? = null

    private fun doOnTextChange(){
        binding.etContact.doAfterTextChanged { text ->
            debounceJob?.cancel()
            debounceJob = CoroutineScope(Dispatchers.Main).launch {
                delay(300)
                println(text)
                if (text.isNullOrEmpty()) {
                    binding.confirmBtn.isEnabled = false
                    //binding.confirmBtn.setOnClickListener { /* Handle Click */ }
                    viewModel.resetContacts()
                } else {
                    viewModel.filterContacts(text.toString())
                    if (text.length == 11) {
                        binding.confirmBtn.isEnabled = true
                        binding.confirmBtn.setOnClickListener {
                            selectOperator(number = text.toString())
                        }
                    } else {
                        binding.confirmBtn.isEnabled = false
                        //binding.confirmBtn.setOnClickListener { /* Handle Click */ }
                    }
                }
            }
        }
    }

    private fun setUpRecent(recentContacts: MutableList<Contacts>) {
        binding.recentContacts.setVisibility(recentContacts.isNotEmpty())
        binding.rvRecentContactsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRecentContactsList.adapter = adapter2
        adapter2.submitList(recentContacts)
    }

    private fun setupRecyclerView(contacts: MutableList<Contacts>) {
        binding.allContacts.setVisibility(contacts.isNotEmpty())
        binding.rvAllContactsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAllContactsList.adapter = adapter
        adapter.submitList(contacts)
    }
}