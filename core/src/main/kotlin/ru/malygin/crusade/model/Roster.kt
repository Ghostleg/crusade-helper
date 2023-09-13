package ru.malygin.crusade.model

import ru.malygin.crusade.model.battle.BattleLog
import ru.malygin.crusade.model.unit.CrusadeUnit

data class Roster(
    val forceName: String,
    val faction: Faction,
    val crusadeUnits: List<CrusadeUnit>,
    val supplyLimit: Int,
    val supplyUsed: Int,
    val battlesTally: Int,
    val battlesWon: Int,
    val requisitionPoints: Int,
    val requisitionLog: List<RequisitionLog>,
    val battleLog: List<BattleLog>,
)
