package ru.malygin.crusade.model

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import ru.malygin.crusade.model.codex.BattleHonour
import ru.malygin.crusade.model.codex.BattleScar
import ru.malygin.crusade.model.codex.UnitCodexParameters
import ru.malygin.crusade.model.codex.UnitKeyword

data class CrusadeUnit @BsonCreator constructor(
    @BsonProperty("_id") val _id : ObjectId,
    @BsonProperty("name") val name: String,
    @BsonProperty("story")val story: String? = "",
    @Suppress("ArrayInDataClass")
    @BsonProperty("unitPhoto") val unitPhoto: ByteArray? = null,
    @BsonProperty("codexParams") val codexParams: UnitCodexParameters,
    @BsonProperty("crusadeFaction") val crusadeFaction: String,
    @BsonProperty("selectableKeywords") val selectableKeywords: List<UnitKeyword>,
    @BsonProperty("battleHonours") val battleHonours: List<BattleHonour> = emptyList(),
    @BsonProperty("battleScars") val battleScars: List<BattleScar> = emptyList(),
    @BsonProperty("experience") val experience: BattleExperience = BattleExperience()
)
