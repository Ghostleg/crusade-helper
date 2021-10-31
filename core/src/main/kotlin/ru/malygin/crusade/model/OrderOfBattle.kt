package ru.malygin.crusade.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class OrderOfBattle constructor(
    @Id val _id: ObjectId,
    val faction: String,
    val playerName: String,
    val playerNickname: String,
    @Suppress("ArrayInDataClass")
    val forceLogo: ByteArray? = null,
    @DBRef
    val forces: List<CrusadeUnit> = emptyList(),
    val battleTally: Int = 0,
    val battlesWon: Int = 0,
    val requisitionPoints: Int = 5,
    val supplyLimit: Int = 50,
    val supplyUsed: Int = 0,
    val crusadePoints: Int = 2,
)