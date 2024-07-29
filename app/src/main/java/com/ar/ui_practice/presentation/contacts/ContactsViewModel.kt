package com.ar.ui_practice.presentation.contacts

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ar.ui_practice.data.model.Contacts
import kotlin.random.Random

class ContactsViewModel: ViewModel() {

    var allContactList =  MutableLiveData<MutableList<Contacts>>()
        private set

    var recentContactList =  MutableLiveData<MutableList<Contacts>>()
        private set


    fun loadContacts(context: Context) {
        val contacts = mutableListOf<Contacts>()
        val recentContacts = mutableListOf<Contacts>()
        val contactIdSet = mutableSetOf<String>()

        val contentResolver = context.contentResolver
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

        allContactList.value = contacts
        recentContactList.value = recentContacts
    }

}