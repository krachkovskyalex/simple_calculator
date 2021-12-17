package com.krachkovsky.mycalkulator

import kotlin.system.exitProcess


fun main() {
    var prevResult = ""

    while (true) {
        val calculator = Calculator(User(prevResult).input())
        val counter = Counter(CalculateCommand(calculator))
        counter.calculate()
        prevResult = calculator.result.toString()
        //new comment
        while (true) {
            print("To continue with previous result print - pr,\n" +
                    "to clear and continue - c\n" +
                    "to exit - e\n" +
                    "-> ")
            val read = readLine()?.lowercase()
            if (read == "e") {
                exitProcess(0)
            } else if (read == "c") {
                prevResult = ""
                break
            } else if (read == "pr") {
                break
            } else {
                println("Incorrect input! Try again")
            }
        }
    }
}

