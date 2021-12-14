package com.krachkovsky.mycalkulator


class CalculateCommand(private val calculator: Calculator) : Command {
    override fun execut() {
        calculator.getResult()
    }
}