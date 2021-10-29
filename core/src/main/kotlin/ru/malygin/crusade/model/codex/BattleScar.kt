package ru.malygin.crusade.model.codex

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

data class BattleScar @BsonCreator constructor(
    @BsonProperty("name") val name: String,
    @BsonProperty("faction") val faction: CodexFaction,
    @BsonProperty("battlefieldRole") val battlefieldRole: BattlefieldRole,
    @BsonProperty("description") val description: String
)
