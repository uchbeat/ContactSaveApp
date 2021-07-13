package com.example.contactsaveapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactsaveapp.models.Contact
import com.example.contactsaveapp.models.ContactDatabase


class MainActivityViewModel:ViewModel() {
    val contactsLiveData = MutableLiveData<List<Contact>>()

    fun getContacts(database: ContactDatabase){
        contactsLiveData.postValue(database.contactDAO().getAllContacts())
    }

    fun addContact(database: ContactDatabase, contact: Contact){
        database.contactDAO().addContact(contact)
        getContacts(database)
    }
}