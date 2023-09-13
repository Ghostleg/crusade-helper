package ru.malygin.crusade.model.battle

import ru.malygin.crusade.model.Faction
import java.time.ZonedDateTime

data class BattleLog(
    val date: ZonedDateTime,
    val size: BattleSize,
    val points: Int,
    val mission: Mission,
    val opponent: String,
    val opponentFaction: Faction,
    val yourScore: Int,
    val opponentScore: Int,
    val agendas: List<Agenda>,
    val unitsResult: Map<Unit, UnitResult>,
)
