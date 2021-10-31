package ru.malygin.crusade.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.DBRef
import ru.malygin.crusade.model.codex.BattleHonour
import ru.malygin.crusade.model.codex.BattleScar
import ru.malygin.crusade.model.codex.UnitCodexParameters
import ru.malygin.crusade.model.codex.UnitKeyword

data class CrusadeUnit constructor(
    val _id: ObjectId,
    val name: String,
    val story: String? = "",
    @Suppress("ArrayInDataClass")
    val unitPhoto: ByteArray? = null,
    val codexParams: UnitCodexParameters,
    val crusadeFaction: String,
    val selectableKeywords: List<UnitKeyword>,
    @DBRef
    val battleHonours: List<BattleHonour> = emptyList(),
    @DBRef
    val battleScars: List<BattleScar> = emptyList(),
    val experience: BattleExperience = BattleExperience()
)
