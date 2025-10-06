package com.example.testtaskapp

data class SheduleItem (
    val time: String,
    val subject: String,
    val type: String,
    val teacher: String
)

data class SheduleDay (
    val date: String,
    val dayOfWeek: String,
    val sheduleItems: List<SheduleItem>
)
