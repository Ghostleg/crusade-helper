package ru.malygin.crusade.model

data class BattleExperience(
    val experience: Int,
    val unitsKilled: Int,
    val markedForGreatness: Int,
    val battlesPlayed: Int,
    val battlesSurvived: Int,
)