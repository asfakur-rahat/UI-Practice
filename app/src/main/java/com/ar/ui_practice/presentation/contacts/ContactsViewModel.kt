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

    private var mContacts = mutableListOf<Contacts>()
    private var mRecentContacts = mutableListOf<Contacts>()

    private fun formatPhoneNumber(input: String): String {

        val cleanedInput = input.filter { it.isDigit() }
        val withoutCountryCode = if (cleanedInput.startsWith("88")) {
            cleanedInput.removePrefix("88")
        } else {
            cleanedInput
        }

        return if (withoutCountryCode.startsWith("0")) {
            withoutCountryCode
        } else {
            "0$withoutCountryCode"
        }
    }


    fun loadContacts(context: Context) {
        val contacts = mutableListOf<Contacts>()
        val recentContacts = mutableListOf<Contacts>()
        val contactIdSet = mutableSetOf<String>()

        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER),
            null, null, null
        )

        cursor?.let {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var ok = false

            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)
                val formattedNumber = formatPhoneNumber(number)
                if(contactIdSet.add(formattedNumber)){
                    ok = true
                    contacts.add(Contacts(name, formattedNumber))
                }
                if(Random.nextInt(1,10) %2 ==0){
                    if(ok && recentContacts.size <= 4){
                        recentContacts.add(Contacts(name, formattedNumber))
                        ok = false
                    }
                }
            }
            it.close()
        }
        contacts.sortBy { it.name }
        recentContacts.sortBy { it.name }

        mContacts = contacts
        mRecentContacts = recentContacts

        allContactList.value = contacts
        recentContactList.value = recentContacts
    }
    fun resetContacts(){
        allContactList.value = mContacts
        recentContactList.value = mRecentContacts
    }
    fun filterContacts(query: String){
        val filteredList = mContacts.filter {
            it.name.contains(query, ignoreCase = true) || it.number.contains(query, ignoreCase = true)
        }
        val filteredRecentList = mRecentContacts.filter {
            it.name.contains(query, ignoreCase = true) || it.number.contains(query, ignoreCase = true)
        }
        allContactList.value = filteredList.toMutableList()
        recentContactList.value = filteredRecentList.toMutableList()
    }

}