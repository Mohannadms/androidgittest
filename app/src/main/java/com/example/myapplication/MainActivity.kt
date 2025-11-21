package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var contactDao: ContactDao
    private lateinit var contactsAdapter: ContactAdapter
    private var allContacts = emptyList<ContactEntity>()

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var categorySpinner: Spinner
    private lateinit var filterButton: Button
    private lateinit var showAllButton: Button
    private lateinit var contactsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)
        contactDao = db.contactDao()

        nameEditText = findViewById(R.id.edit_text_name)
        phoneEditText = findViewById(R.id.edit_text_phone)
        categoryEditText = findViewById(R.id.edit_text_category)
        saveButton = findViewById(R.id.button_save)
        categorySpinner = findViewById(R.id.spinner_category)
        filterButton = findViewById(R.id.button_filter)
        showAllButton = findViewById(R.id.button_show_all)
        contactsListView = findViewById(R.id.list_view_contacts)

        contactsAdapter = ContactAdapter(this, allContacts)
        contactsListView.adapter = contactsAdapter

        contactDao.getAllContacts().observe(this, Observer { contacts ->
            allContacts = contacts
            updateListView(allContacts)
            updateCategorySpinner()
        })

        saveButton.setOnClickListener {
            saveContact()
        }

        filterButton.setOnClickListener {
            filterContacts()
        }

        showAllButton.setOnClickListener {
            updateListView(allContacts)
        }

        contactsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contactsAdapter.getItem(position)
            openDialer(selectedContact?.phone)
        }
    }

    private fun saveContact() {
        val name = nameEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val category = categoryEditText.text.toString().trim()

        if (name.isEmpty() || phone.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val contact = ContactEntity(name = name, phone = phone, category = category)

        lifecycleScope.launch(Dispatchers.IO) {
            contactDao.insertContact(contact)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Contact Saved!", Toast.LENGTH_SHORT).show()
                nameEditText.text.clear()
                phoneEditText.text.clear()
                categoryEditText.text.clear()
            }
        }
    }

    private fun updateListView(contacts: List<ContactEntity>) {
        contactsAdapter = ContactAdapter(this, contacts)
        contactsListView.adapter = contactsAdapter
    }

    private fun updateCategorySpinner() {
        contactDao.getAllCategories().observe(this, Observer { categories ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        })
    }

    private fun filterContacts() {
        if (categorySpinner.selectedItem != null) {
            val selectedCategory = categorySpinner.selectedItem.toString()

            contactDao.getContactsByCategory(selectedCategory).observe(this, Observer { filteredContacts ->
                updateListView(filteredContacts)
            })
        } else {
            Toast.makeText(this, "No category selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDialer(phoneNumber: String?) {
        if (phoneNumber.isNullOrEmpty()) return

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No dialer app found.", Toast.LENGTH_SHORT).show()
        }
    }
}