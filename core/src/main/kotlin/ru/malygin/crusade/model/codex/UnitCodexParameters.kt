package ru.malygin.crusade.model.codex

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty


data class UnitCodexParameters @BsonCreator constructor(
    @BsonProperty("battlefieldRole") val battlefieldRole: BattlefieldRole,
    @BsonProperty("powerRating") val powerRating: Int,
    /*@BsonProperty("unitType") val unitType: UnitType,*/
    @BsonProperty("equipment") val equipment: List<Equipment> = emptyList(),
)
