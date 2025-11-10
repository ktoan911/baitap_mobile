package com.example.number_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var edtNumber: EditText
    private lateinit var lvNumbers: ListView
    private lateinit var tvEmpty: TextView
    private lateinit var cbOdd: CheckBox
    private lateinit var cbPrime: CheckBox
    private lateinit var cbPerfect: CheckBox
    private lateinit var cbEven: CheckBox
    private lateinit var cbSquare: CheckBox
    private lateinit var cbFibonacci: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNumber = findViewById(R.id.edtNumber)
        lvNumbers = findViewById(R.id.lvNumbers)
        tvEmpty = findViewById(R.id.tvEmpty)
        cbOdd = findViewById(R.id.cbOdd)
        cbPrime = findViewById(R.id.cbPrime)
        cbPerfect = findViewById(R.id.cbPerfect)
        cbEven = findViewById(R.id.cbEven)
        cbSquare = findViewById(R.id.cbSquare)
        cbFibonacci = findViewById(R.id.cbFibonacci)

        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val checkBoxes = listOf(cbOdd, cbPrime, cbPerfect, cbEven, cbSquare, cbFibonacci)
        for (cb in checkBoxes) {
            cb.setOnCheckedChangeListener { _, _ ->
                updateList()
            }
        }

        updateList()
    }

    private fun updateList() {
        val input = edtNumber.text.toString()
        if (input.isEmpty()) {
            showEmpty()
            return
        }

        val n = input.toIntOrNull() ?: 0
        if (n <= 0) {
            showEmpty()
            return
        }

        val selectedSets = mutableListOf<Set<Int>>()

        if (cbOdd.isChecked) {
            selectedSets.add(getOddNumbers(n).toSet())
        }
        if (cbPrime.isChecked) {
            selectedSets.add(getPrimeNumbers(n).toSet())
        }
        if (cbPerfect.isChecked) {
            selectedSets.add(getPerfectNumbers(n).toSet())
        }
        if (cbEven.isChecked) {
            selectedSets.add(getEvenNumbers(n).toSet())
        }
        if (cbSquare.isChecked) {
            selectedSets.add(getSquareNumbers(n).toSet())
        }
        if (cbFibonacci.isChecked) {
            selectedSets.add(getFibonacciNumbers(n).toSet())
        }

        val numbers = if (selectedSets.isEmpty()) {
            listOf()
        } else {
            var result = selectedSets[0]
            for (i in 1 until selectedSets.size) {
                result = result.intersect(selectedSets[i])
            }
            result.sorted()
        }

        if (numbers.isEmpty()) {
            showEmpty()
        } else {
            showList(numbers)
        }
    }

    private fun showEmpty() {
        tvEmpty.visibility = View.VISIBLE
        lvNumbers.visibility = View.GONE
    }

    private fun showList(numbers: List<Int>) {
        tvEmpty.visibility = View.GONE
        lvNumbers.visibility = View.VISIBLE
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        lvNumbers.adapter = adapter
    }

    private fun getOddNumbers(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 1 until n) {
            if (i % 2 != 0) {
                result.add(i)
            }
        }
        return result
    }

    private fun getEvenNumbers(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 2 until n) {
            if (i % 2 == 0) {
                result.add(i)
            }
        }
        return result
    }

    private fun getPrimeNumbers(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 2 until n) {
            if (isPrime(i)) {
                result.add(i)
            }
        }
        return result
    }

    private fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        if (num == 2) return true
        if (num % 2 == 0) return false
        for (i in 3..sqrt(num.toDouble()).toInt() step 2) {
            if (num % i == 0) return false
        }
        return true
    }

    private fun getPerfectNumbers(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 1 until n) {
            if (isPerfect(i)) {
                result.add(i)
            }
        }
        return result
    }

    private fun isPerfect(num: Int): Boolean {
        if (num < 2) return false
        var sum = 1
        for (i in 2..sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) {
                sum += i
                if (i != num / i) {
                    sum += num / i
                }
            }
        }
        return sum == num
    }

    private fun getSquareNumbers(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        var i = 1
        while (i * i < n) {
            result.add(i * i)
            i++
        }
        return result
    }

    private fun getFibonacciNumbers(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        var a = 0
        var b = 1
        while (a < n) {
            if (a > 0) {
                result.add(a)
            }
            val temp = a + b
            a = b
            b = temp
        }
        return result
    }
}

