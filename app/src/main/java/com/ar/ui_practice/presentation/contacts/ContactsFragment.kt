package com.ar.ui_practice.presentation.contacts

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.AllContactsAdapter
import com.ar.ui_practice.data.model.Contacts
import com.ar.ui_practice.databinding.FragmentContactsBinding
import kotlin.random.Random

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
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToTopUpFragment(it.name, it.number, args.title))
        }
        adapter2 = AllContactsAdapter{
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToTopUpFragment(it.name, it.number, args.title))
        }
        initView()
        initListener()
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        //binding.
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
                }else{
                    binding.confirmBtn.setImageResource(R.drawable.ic_action_bth_active)
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