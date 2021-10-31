package ru.malygin.crusade.model

data class BattleExperience constructor(
    val experience: Int = 0,
    val unitsKilled: Int = 0,
    val markedForGreatness: Int = 0,
    val battlesPlayed: Int = 0,
    val battlesSurvived: Int = 0,
)