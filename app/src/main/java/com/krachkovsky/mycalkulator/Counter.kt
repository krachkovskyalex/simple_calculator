package com.krachkovsky.mycalkulator

class Counter(private val calculate: Command) {
    fun calculate() {
        calculate.execut()
    }
}