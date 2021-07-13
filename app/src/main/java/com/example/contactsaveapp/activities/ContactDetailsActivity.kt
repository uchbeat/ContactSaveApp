package com.example.contactsaveapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactsaveapp.databinding.ActivityContactDetailsBinding


class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContactDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run{
            contactID.text=intent.getIntExtra("id",0).toString()
            contactDisplay.text=intent.getStringExtra("name")
            phoneNumberDisplay.text=intent.getStringExtra("number")

        }
    }
}