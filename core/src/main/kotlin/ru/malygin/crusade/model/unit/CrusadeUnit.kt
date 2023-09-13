package ru.malygin.crusade.model.unit

import ru.malygin.crusade.model.CustomTally

data class CrusadeUnit(
    val name: String,
    val type: String,
    val pointCost: Int,
    val crusadePoints: Int,
    val keywords: List<String>,
    val equipment: List<String>,
    val enhancement: List<Enhancement>,
    val battleHonours: List<BattleHonour>,
    val battleScars: List<BattleScar>,
    val battlePlayed: Int,
    val battleSurvived: Int,
    val unitsDestroyed: Int,
    val customTallies: List<CustomTally>,
    val rank: Rank,
)
