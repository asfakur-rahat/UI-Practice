package com.ar.ui_practice.presentation.contacts

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.ui_practice.R
import com.ar.ui_practice.adapter.AllContactsAdapter
import com.ar.ui_practice.data.model.Contacts
import com.ar.ui_practice.databinding.FragmentContactsBinding
import kotlin.random.Random

class ContactsFragment : Fragment(R.layout.fragment_contacts){

    private lateinit var binding: FragmentContactsBinding
    private lateinit var adapter: AllContactsAdapter
    private lateinit var adapter2: AllContactsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentContactsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        adapter = AllContactsAdapter{
            //Toast.makeText(requireContext(), "Clicked on All Contacts ${it.name}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToTopUpFragment())
        }
        adapter2 = AllContactsAdapter{
            Toast.makeText(requireContext(), "Clicked on Recent Contacts ${it.name}", Toast.LENGTH_SHORT).show()
        }
        initView()
        loadContacts()
        initListener()
    }

    private fun initListener() {
        binding.actionBar.ivNavIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = "Mobile Recharge"
    }


    private fun loadContacts() {
        val contacts = mutableListOf<Contacts>()
        val recentContacts = mutableListOf<Contacts>()
        val contactIdSet = mutableSetOf<String>()

        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER),
            null, null, null
        )

        cursor?.let {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var ok = false

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)
                if(contactIdSet.add(id)){
                    ok = true
                    contacts.add(Contacts(name, number))
                }
                if(Random.nextInt(1,10) %2 ==0){
                    if(ok && recentContacts.size <= 4){
                        recentContacts.add(Contacts(name, number))
                        ok = false
                    }
                }
            }
            it.close()
        }
        contacts.sortBy { it.name }
        recentContacts.sortBy { it.name }
        setupRecyclerView(contacts)
        setUpRecent(recentContacts)
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