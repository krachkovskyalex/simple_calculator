package com.krachkovsky.mycalkulator

import kotlin.system.exitProcess


class User(private val prevResult: String) {
    fun input(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var num: String
        var position = 0
        var incorrect: Boolean
        var characters: Boolean
        var usePeriod: Boolean
        var moreThanOnePoint: Boolean
        var openedBrackets: Int
        var closedBrackets: Int
        var dotsInNumber = 0
        do {
            incorrect = false
            characters = false
            usePeriod = false
            moreThanOnePoint = false
            openedBrackets = 0
            closedBrackets = 0
            print("Enter a line to calculate\n" +
                    "(only numbers, '/', '*', '-', '+', '.', no spaces)\n" +
                    "-> $prevResult")
            num = ""
            list.clear()
            if (prevResult.isNotEmpty()) {
                list.add(prevResult)
            }
            val a = readLine()
                ?.filter {
                    it.isDigit() ||
                            when (it) {
                                '.',
                                ',',
                                '/',
                                '*',
                                '-',
                                '+',
                                '(',
                                ')' -> true
                                else -> {
                                    incorrect = true
                                    false
                                }
                            }
                }
                ?.toCharArray()
            a?.let {
                for (i in a) {
                    if (i.isDigit() || i == '.') {
                        num += i
                    } else {
                        if (num != "") {
                            list.add(num)
                        }
                        list.add(i.toString())
                        num = ""
                    }
                }
                list.add(num)
            }
            while (position < list.size) {
                when (list[position]) {
                    "(" -> openedBrackets++
                    ")" -> closedBrackets++
                }
                for (i in list[position].toString()) {
                    when (i) {
                        '.' -> dotsInNumber++
                        ',' -> usePeriod = true
                    }
                }
                if (dotsInNumber > 1) {
                    moreThanOnePoint = true
                }
                dotsInNumber = 0
                position++
            }
            for (i in 0 until list.size - 1) {
                if ((list[i] == "/" || list[i] == "*" || list[i] == "-" || list[i] == "+") &&
                    (list[i + 1] == "/" || list[i + 1] == "*" || list[i + 1] == "-" || list[i + 1] == "+")
                ) {
                    incorrect = true
                    characters = true
                }
            }
            when {
                moreThanOnePoint -> {
                    println("More than one decimal point")
                    incorrect = true
                }
                openedBrackets != closedBrackets -> {
                    incorrect = true
                    println("You have problems with parentheses")
                }
                usePeriod -> {
                    incorrect = true
                    println("Use a period instead of a comma")
                }
                characters -> {
                    incorrect = true
                    println("You have sign problems")
                }

            }
            if (incorrect) {
                while (true) {
                    val answer = tryAgain()
                    if (answer == "exit") {
                        exitProcess(0)
                    } else if (answer == "try") {
                        break
                    }
                }
            }
        } while (incorrect)
        return list
    }

    private fun tryAgain(): String? {
        print("Incorrect input.\n" +
                "To try again print - try\n" +
                "to exit - exit) -> ")
        return readLine()?.lowercase()
    }
}