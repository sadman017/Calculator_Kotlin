package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Calculator : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentOperator: String? = null
    private var firstValue: Double = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        display = findViewById(R.id.tvDisplay)

        val buttons = listOf(
            R.id.tb21, R.id.tb22, R.id.tb23, R.id.tb31, R.id.tb32, R.id.tb33,
            R.id.tb41, R.id.tb42, R.id.tb43, R.id.tb51
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.tb11).setOnClickListener { clearDisplay() }
        findViewById<Button>(R.id.tb44).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.tb34).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.tb24).setOnClickListener { setOperator("×") }
        findViewById<Button>(R.id.tb14).setOnClickListener { setOperator("÷") }
        findViewById<Button>(R.id.tb53).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.tb52).setOnClickListener { appendDot() }
        findViewById<Button>(R.id.tb12).setOnClickListener { deleteLastCharacter() }
        findViewById<Button>(R.id.tb13).setOnClickListener { percentage() }

    }

    private fun appendNumber(number: String) {
        if (isNewOperation) {
            display.text = number
            isNewOperation = false
        } else {
            display.text = display.text.toString() + number
        }
    }

    private fun clearDisplay() {
        display.text = "0"
        currentOperator = null
        firstValue = 0.0
        isNewOperation = true
    }

    private fun setOperator(operator: String) {
        if (display.text.isNotEmpty()) {
            firstValue = display.text.toString().toDouble()
            currentOperator = operator
            isNewOperation = true
        }
    }

    private fun calculateResult() {
        if (currentOperator != null && display.text.isNotEmpty()) {
            val secondValue = display.text.toString().toDouble()
            val result = when (currentOperator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "×" -> firstValue * secondValue
                "÷" -> if (secondValue != 0.0) firstValue / secondValue else Double.NaN
                else -> 0.0
            }
            display.text = result.toString()
            currentOperator = null
            isNewOperation = true
        }
    }

    private fun appendDot() {
        if (!display.text.contains(".")) {
            display.text = display.text.toString() + "."
        }
    }

    private fun deleteLastCharacter() {
        if (display.text.isNotEmpty()) {
            display.text = display.text.substring(0, display.text.length - 1)
            if (display.text.isEmpty()) {
                display.text = "0"
            }
        }
    }

    private fun percentage() {
        if (display.text.isNotEmpty()) {
            val number = display.text.toString().toDouble()
            val result = number / 100
            display.text = result.toString()
        }
    }
}