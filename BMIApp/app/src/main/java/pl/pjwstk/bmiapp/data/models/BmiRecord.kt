package pl.pjwstk.bmiapp.data.models

import java.util.Date

data class BmiRecord(
    val id: Int,
    val date: Date,
    val bmiValue: Float,
    val weight: Float,
    val height: Float
)