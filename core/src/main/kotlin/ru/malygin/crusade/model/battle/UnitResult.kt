package ru.malygin.crusade.model.battle

data class UnitResult(
    val unitsDestroyed: Int,
    val agendaXp: Int,
    val destroyed: Boolean,
    val markedForGreatness: Boolean,
)
