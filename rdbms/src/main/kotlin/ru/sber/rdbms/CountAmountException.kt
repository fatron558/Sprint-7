package ru.sber.rdbms

import java.lang.Exception

class CountAmountException(private val description: String = "Недостаточно средств") : Exception(description)