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
    private var currentInput = ""
    private var operator = ""
    private var result = 0.0
    private var lastInput = ""
    private var newInput = true

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
        findViewById<Button>(R.id.tb52).setOnClickListener { appendPoint() }
        findViewById<Button>(R.id.tb12).setOnClickListener { invertSign() }
        findViewById<Button>(R.id.tb13).setOnClickListener { percent() }
    }

    private fun appendNumber(number: String) {
        if (newInput) {
            currentInput = ""
            newInput = false
        }
        currentInput += number
        display.text = currentInput
    }

    private fun setOperator(op: String) {
        if (!newInput) {
            calculateResult()
        }
        operator = op
        lastInput = currentInput

        currentInput += " $operator "
        newInput = false
        display.text = currentInput
    }

    private fun appendPoint() {
        if(!currentInput.contains(".") || lastInput.contains(".")) {
            currentInput += "."
            display.text = currentInput
        }
    }

    private fun calculateResult() {
        val firstOperand = lastInput.toDoubleOrNull() ?: 0.0
        val secondOperand = currentInput.split(" ").last().toDoubleOrNull() ?: 0.0

        result = when (operator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "×" -> firstOperand * secondOperand
            "÷" -> if (secondOperand != 0.0) firstOperand / secondOperand else 0.0
            else -> secondOperand
        }

        val displayResult = if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }

        display.text = displayResult

        currentInput = displayResult
        newInput = true
    }

    private fun clearDisplay() {
        currentInput = "0"
        lastInput = ""
        operator = ""
        result = 0.0
        newInput = true
        display.text = currentInput
    }

    private fun invertSign() {
        currentInput = (currentInput.toDoubleOrNull()?.times(-1)).toString()
        display.text = currentInput
    }

    private fun percent() {
        currentInput = (currentInput.toDoubleOrNull()?.div(100)).toString()
        display.text = currentInput
    }
}
