package com.example.money_exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextFrom: EditText
    private lateinit var editTextTo: EditText
    
    private var isUpdating = false

    private val exchangeRates = mapOf(
        "VND" to 1.0,
        "USD" to 26_300.0,
        "EUR" to 30_430.0,
        "JPY" to 190.0,
        "GBP" to 35_000.0,
        "AUD" to 17_000.0,
        "CAD" to 19_000.0,
        "CHF" to 28_000.0,
        "CNY" to 3_600.0,
        "KRW" to 18.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editTextFrom = findViewById(R.id.editTextFrom)
        editTextTo = findViewById(R.id.editTextTo)

        setupSpinners()
        setupEditTexts()
    }

    private fun setupSpinners() {
        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
        
        spinnerFrom.setSelection(0) // VND
        spinnerTo.setSelection(1) // USD

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupEditTexts() {
        editTextFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating && editTextFrom.hasFocus()) {
                    convertCurrency()
                }
            }
        })

        editTextTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating && editTextTo.hasFocus()) {
                    convertCurrencyReverse()
                }
            }
        })
    }

    private fun convertCurrency() {
        val amountStr = editTextFrom.text.toString()
        if (amountStr.isEmpty()) {
            isUpdating = true
            editTextTo.setText("")
            isUpdating = false
            return
        }

        try {
            val amount = amountStr.toDouble()
            val fromCurrency = getCurrencyCode(spinnerFrom.selectedItemPosition)
            val toCurrency = getCurrencyCode(spinnerTo.selectedItemPosition)

            val fromRate = exchangeRates[fromCurrency] ?: 1.0
            val toRate = exchangeRates[toCurrency] ?: 1.0

            // chuyen ve VND roi chuyen sang dong tien khac
            val amountInVND = amount * fromRate
            val result = amountInVND / toRate

            isUpdating = true
            editTextTo.setText(String.format("%.2f", result))
            isUpdating = false
        } catch (e: Exception) {
            // khong lam gi neu loi
        }
    }

    private fun convertCurrencyReverse() {
        val amountStr = editTextTo.text.toString()
        if (amountStr.isEmpty()) {
            isUpdating = true
            editTextFrom.setText("")
            isUpdating = false
            return
        }

        try {
            val amount = amountStr.toDouble()
            val fromCurrency = getCurrencyCode(spinnerFrom.selectedItemPosition)
            val toCurrency = getCurrencyCode(spinnerTo.selectedItemPosition)

            val fromRate = exchangeRates[fromCurrency] ?: 1.0
            val toRate = exchangeRates[toCurrency] ?: 1.0

            // chuyen nguoc lai
            val amountInVND = amount * toRate
            val result = amountInVND / fromRate

            isUpdating = true
            editTextFrom.setText(String.format("%.2f", result))
            isUpdating = false
        } catch (e: Exception) {
            // khong lam gi neu loi
        }
    }

    private fun getCurrencyCode(position: Int): String {
        return when (position) {
            0 -> "VND"
            1 -> "USD"
            2 -> "EUR"
            3 -> "JPY"
            4 -> "GBP"
            5 -> "AUD"
            6 -> "CAD"
            7 -> "CHF"
            8 -> "CNY"
            9 -> "KRW"
            else -> "VND"
        }
    }
}

