package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityMainBinding
import javax.script.ScriptEngineManager

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val expression = StringBuilder()
        var openBrackets = 0
        var currentNumberLength = 0

        fun signChangeOperation()
        {
//            if (unaryMinusStatus)
//            {
//                openBrackets++
//                if (expression.isEmpty())
//                {
//                    expressionForEqual.append("(-")
//                    expressionForView.append("(-")
//                }
//                else
//                {
//                    expressionForEqual.insert(expressionForEqual.length - 1 - currentNumberLength, "(-")
//                    expressionForView.insert(expressionForView.length - 1 - currentNumberLength, "(-")
//                }
//            }
//            else
//            {
//                openBrackets--
//                if (expressionForEqual.length == 2)
//                {
//                    expressionForEqual.setLength(0)
//                    expressionForView.setLength(0)
//                }
//                else
//                {
//                    expressionForEqual.delete(expressionForEqual.length - 3 - currentNumberLength, expressionForEqual.length - 1 - currentNumberLength)
//                    expressionForView.delete(expressionForView.length - 3 - currentNumberLength, expressionForView.length - 1 - currentNumberLength)
//                }
//            }
        }


        fun currentNumberLengthCheck() {
            if (expression.isNotEmpty()) {
                if (expression.last() in '0'..'9' || expression.last() == ',')
                    currentNumberLength++
                else
                    currentNumberLength = 0
            }
            else
                currentNumberLength = 0
        }

        fun expressionCorrectToEqual(string: String): String {
            var temp = string
            temp = temp.replace(",", ".")
            temp = temp.replace("%", "*0.01")
            temp = temp.replace("×", "*")
            temp = temp.replace("÷", "/")
            return temp
        }

        fun expressionCorrectToView(string: String): String {
            return string.replace(".", ",")
        }

        fun equalOperation() {
            val engine = ScriptEngineManager().getEngineByName("js")
            val result = StringBuilder(engine.eval(expressionCorrectToEqual(expression.toString())).toString())
            expression.setLength(0)
            openBrackets = 0

            if (result.endsWith(".0"))
                result.delete(result.length - 2, result.length)

            currentNumberLength = result.length
            expression.append(expressionCorrectToView(result.toString()))
        }

        fun clearOperation() {
            expression.setLength(0)
            openBrackets = 0
            currentNumberLength = 0
        }

        fun deleteOperation() {
            if (expression.isNotEmpty()) {
                if (expression.last() == '(')
                    openBrackets--
                else if (expression.last() in '0'..'9' || expression.last() == '.')
                    currentNumberLength--
                expression.setLength(expression.length - 1)
            }
        }

        fun addSymbolToExpression(key: String)
        {
            if (key == "+" || key == "-" || key == "×" || key == "÷" || key == "%" || key == ",") {
                if (expression.isNotEmpty())
                    if (expression.last() != '+' && expression.last() != '-' && expression.last() != '×' && expression.last() != '÷' && expression.last() != ',') {
                        expression.append(key)
                        currentNumberLengthCheck()
                    }
                return
            }
            if (key == "()") {
                if (expression.isEmpty()) {
                    expression.append("(")
                    openBrackets++
                }
                else {
                    if (expression.last() == '+' || expression.last() == '-' || expression.last() == '×' || expression.last() == '÷' || expression.last() == '(') {
                        expression.append("(")
                        openBrackets++
                    }
                    else if (expression.last() == '%' || expression.last() == ')') {
                        expression.append("*(")
                        openBrackets++
                    }
                    else if (expression.last() in '0'..'9') {
                        if (openBrackets == 0) {
                            expression.append("(*")
                            openBrackets++
                        }
                        else {
                            expression.append(")")
                            openBrackets--
                        }
                    }
                    else if (expression.last() == ',') {
                        expression.setLength(expression.length - 1)
                        expression.append("*(")
                    }
                }
                currentNumberLengthCheck()
                return
            }
            if (key == "0")
            {
                if (expression.isEmpty()) {
                    expression.append(key)
                    currentNumberLengthCheck()
                }
                else {
                    if (expression.last() != '0' || currentNumberLength > 1) {
                        expression.append(key)
                        currentNumberLengthCheck()
                    }
                }
                return
            }
            if (expression.isNotEmpty())
            {
                if (expression.last() == '0' && currentNumberLength == 1)
                {
                    return
                }
                else
                {
                    expression.append(key)
                    currentNumberLengthCheck()
                }
            }
            else
            {
                expression.append(key)
                currentNumberLengthCheck()
            }
        }

        fun setResultView() {
            binding.ResultText.text = expression
        }

        fun resultClick(button: String)
        {
            when (button) {
                "one" -> addSymbolToExpression("1")
                "two" -> addSymbolToExpression("2")
                "three" -> addSymbolToExpression("3")
                "four" -> addSymbolToExpression("4")
                "five" -> addSymbolToExpression("5")
                "six" -> addSymbolToExpression("6")
                "seven" -> addSymbolToExpression("7")
                "eight" -> addSymbolToExpression("8")
                "nine" -> addSymbolToExpression("9")
                "zero" -> addSymbolToExpression("0")
                "plus" -> addSymbolToExpression("+")
                "minus" -> addSymbolToExpression("-")
                "multiplication" -> addSymbolToExpression("×")
                "divide" -> addSymbolToExpression("÷")
                "comma" -> addSymbolToExpression(",")
                "percent" -> addSymbolToExpression("%")
                "brackets" -> addSymbolToExpression("()")
                "equal" -> equalOperation()
                "clear" -> clearOperation()
                "delete" -> deleteOperation()
                "unaryMinus" -> signChangeOperation()
            }
            setResultView()
        }
        binding.Button1.setOnClickListener { resultClick("one") }
        binding.Button2.setOnClickListener { resultClick("two") }
        binding.Button3.setOnClickListener { resultClick("three") }
        binding.Button4.setOnClickListener { resultClick("four") }
        binding.Button5.setOnClickListener { resultClick("five") }
        binding.Button6.setOnClickListener { resultClick("six") }
        binding.Button7.setOnClickListener { resultClick("seven") }
        binding.Button8.setOnClickListener { resultClick("eight") }
        binding.Button9.setOnClickListener { resultClick("nine") }
        binding.Button0.setOnClickListener { resultClick("zero") }
        binding.ButtonPlus.setOnClickListener { resultClick("plus") }
        binding.ButtonMinus.setOnClickListener { resultClick("minus") }
        binding.ButtonMultiplication.setOnClickListener { resultClick("multiplication") }
        binding.ButtonDivide.setOnClickListener { resultClick("divide") }
        binding.ButtonComma.setOnClickListener { resultClick("comma") }
        binding.ButtonEqual.setOnClickListener { resultClick("equal") }
        binding.ButtonClear.setOnClickListener { resultClick("clear") }
        binding.ButtonDelete.setOnClickListener { resultClick("delete") }
        binding.ButtonPercent.setOnClickListener { resultClick("percent") }
        binding.ButtonUnaryMinus.setOnClickListener { resultClick("unaryMinus") }
        binding.ButtonBrackets.setOnClickListener { resultClick("brackets") }
    }
}

