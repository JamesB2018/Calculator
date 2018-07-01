package com.example.james.calculator

class StringCal {

    fun calculate(calculationList: List<String>): Int {
        var currentOp: String = ""
        var currentNumber = 0

        calculationList.forEach { token ->
            when {
                token.equals("+")
                        || token.equals("/")
                        || token.equals("*")
                        || token.equals("-") -> currentOp = token

                currentOp.equals("-") -> currentNumber -= token.toInt()
                currentOp.equals("/") -> currentNumber /= token.toInt()
                currentOp.equals("*") -> currentNumber *= token.toInt()
                else -> currentNumber += token.toInt()

            }
        }

        return currentNumber
    }
}