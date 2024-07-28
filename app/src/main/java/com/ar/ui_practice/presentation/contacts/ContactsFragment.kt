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
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentContactsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        adapter = AllContactsAdapter{
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToTopUpFragment(it.name, it.number))
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
        //binding.
    }

    private fun initView() {
        binding.actionBar.tvNavTitle.text = "Mobile Recharge"
        initTest()
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