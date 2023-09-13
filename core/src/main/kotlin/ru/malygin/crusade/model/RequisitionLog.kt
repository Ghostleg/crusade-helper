package ru.malygin.crusade.model

import java.time.ZonedDateTime

data class RequisitionLog(
    val date: ZonedDateTime,
    val difference: Int,
    val reason: String,
)
