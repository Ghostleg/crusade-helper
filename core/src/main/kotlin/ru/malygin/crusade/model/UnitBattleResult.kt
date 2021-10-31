package ru.malygin.crusade.model

data class UnitBattleResult(
    val unitsKilled: Int,
    val agendasExperience: Int,
    val experienceGained: Int = 1,
    val killedInAction: Boolean = false,
    val markedForGreatness: Boolean = false,
)