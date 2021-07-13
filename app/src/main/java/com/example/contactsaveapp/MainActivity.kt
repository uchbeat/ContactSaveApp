package com.example.contactsaveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.contactsaveapp.activities.ContactDetailsActivity
import com.example.contactsaveapp.databinding.ActivityMainBinding
import com.example.contactsaveapp.models.Contact
import com.example.contactsaveapp.models.ContactAdapter
import com.example.contactsaveapp.models.ContactDatabase
import com.example.contactsaveapp.viewmodels.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: ContactDatabase
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //instantiating database
        database = Room.databaseBuilder(
            applicationContext, ContactDatabase::class.java,
            "contact_database"
        ).allowMainThreadQueries().build()

        //instantiating viewModel
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getContacts(database)

        //instantiate adapter with empty dataset
        contactAdapter = ContactAdapter(listOf<Contact>()) {
            val intent = Intent(this@MainActivity, ContactDetailsActivity::class.java)
            intent.run {
                putExtra("id", it.id)
                putExtra("name", it.name)
                putExtra("number", it.number)
            }
            startActivity(intent)
        }
        binding.contactsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }

        //observe live data from view model
        viewModel.contactsLiveData.observe(this,{ contacts ->
            contactAdapter.contacts=contacts
            contactAdapter.notifyDataSetChanged()

        })
        binding.saveButton.setOnClickListener(){
            val name = binding.contactName.text.toString()
            val number = binding.contactNumber.text.toString()
            saveContact(name,number)
        }

    }
    private fun saveContact(name: String, number: String) {
        val contact = Contact(id = 0, name, number)
        viewModel.addContact(database,contact)
    }
}