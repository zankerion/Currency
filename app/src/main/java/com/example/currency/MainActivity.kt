package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var filterEditText: EditText
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        filterEditText = findViewById(R.id.filterEditText)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        lifecycleScope.launch {
            val dataLoader = DataLoader()
            val data = dataLoader.loadData("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml")
            adapter.clear()
            adapter.addAll(data)
            adapter.notifyDataSetChanged()
        }

        filterEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // No implementation needed
            }
        })
    }
}