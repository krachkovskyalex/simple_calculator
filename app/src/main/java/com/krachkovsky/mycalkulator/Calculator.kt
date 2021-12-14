package com.krachkovsky.mycalkulator

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.math.RoundingMode

class Calculator(private val list: MutableList<Any>) {
    private var divisionByZero = false
    private var position: Int = 0
    var result = calculate()

    fun getResult() {
        println("Result is $result")
    }

    private fun calculate(): BigDecimal {
        var left = multiply()

        while (position < list.size) {
            val operator = list[position].toString()
            if (operator != "+" && operator != "-") {
                break
            } else {
                position++
            }

            val right = multiply()

            left = if (operator == "+") {
                left.add(right)
            } else {
                left.subtract(right)
            }
        }
        if (divisionByZero) {
            println("Division by 0!")
            return BigDecimal(0)
        }
        return left
    }

    private fun multiply(): BigDecimal {
        var left = factor()

        while (position < list.size) {
            val operator = list[position].toString()
            if (operator != "*" && operator != "/") {
                break
            } else {
                position++
            }

            val right = factor()

            if (operator == "*") {
                left = left.multiply(right)
            } else {
                if (right != BigDecimal(0)) {
                    left = left.divide(right, 20, RoundingMode.HALF_UP)
                } else {
                    divisionByZero = true
                    break
                }
            }
        }
        return left
    }

    private fun factor(): BigDecimal {
        val openBracket = list[position].toString()
        val res: BigDecimal
        if (openBracket == "(") {
            position++
            res = calculate()
            val closingBracket: String
            if (position < list.size) {
                closingBracket = list[position].toString()
            } else {
                throw IllegalArgumentException("Brackets not closed")
            }
            if (closingBracket == ")") {
                position++
                return res
            }
            throw IllegalStateException("')' expected but $closingBracket")
        }
        position++
        return BigDecimal(openBracket)
    }

}