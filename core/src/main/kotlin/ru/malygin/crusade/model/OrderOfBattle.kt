package ru.malygin.crusade.model

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class OrderOfBattle @BsonCreator constructor(
    @BsonProperty("_id") val _id: ObjectId,
    @BsonProperty("faction") val faction: String,
    @BsonProperty("playerName") val playerName: String,
    @BsonProperty("playerNickname") val playerNickname: String,
    @Suppress("ArrayInDataClass")
    @BsonProperty("forceLogo") val forceLogo: ByteArray? = null,
    @BsonProperty("forces")
    val forces: List<CrusadeUnit> = emptyList(),
    @BsonProperty("battleTally") val battleTally: Int = 0,
    @BsonProperty("battlesWon") val battlesWon: Int = 0,
    @BsonProperty("requisitionPoints") val requisitionPoints: Int = 5,
    @BsonProperty("supplyLimit") val supplyLimit: Int = 50,
    @BsonProperty("supplyUsed") val supplyUsed: Int = 0,
    @BsonProperty("crusadePoints") val crusadePoints: Int = 2,
)