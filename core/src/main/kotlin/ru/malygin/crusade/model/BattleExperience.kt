package ru.malygin.crusade.model

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

data class BattleExperience @BsonCreator constructor(
    @BsonProperty("experience") val experience: Int = 0,
    @BsonProperty("unitsKilled") val unitsKilled: Int = 0,
    @BsonProperty("markedForGreatness") val markedForGreatness: Int = 0,
    @BsonProperty("battlesPlayed") val battlesPlayed: Int = 0,
    @BsonProperty("battlesSurvived") val battlesSurvived: Int = 0,
)