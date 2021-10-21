package ru.malygin.crusade.model

import ru.malygin.crusade.model.codex.BattleHonour
import ru.malygin.crusade.model.codex.BattleScar
import ru.malygin.crusade.model.codex.UnitCodexParameters
import ru.malygin.crusade.model.codex.UnitKeyword

data class CrusadeUnit(
    val name: String,
    val story: String? = "",
    @Suppress("ArrayInDataClass")
    val unitPhoto: ByteArray?,
    val codexParams: UnitCodexParameters,
    val crusadeFaction: String,
    val selectableKeywords: List<UnitKeyword>,
    val battleHonours: List<BattleHonour>,
    val battleScars: List<BattleScar>,
    val experience: BattleExperience
)
