package ru.malygin.crusade.model.codex

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

data class BattleHonour @BsonCreator constructor(
    @BsonProperty("name") val name: String,
    @BsonProperty("faction") val faction: CodexFaction,
    @BsonProperty("acceptableKeywords") val acceptableKeywords: List<UnitKeyword>,
    @BsonProperty("excludingKeywords") val excludingKeywords: List<UnitKeyword>? = emptyList(),
    @BsonProperty("description") val description: String,
)