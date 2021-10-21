package ru.malygin.crusade.model

data class OrderOfBattle(
    val faction: String,
    val playerName: String,
    val playerNickname: String,
    @Suppress("ArrayInDataClass")
    val forceLogo: ByteArray,
    val forces: List<CrusadeUnit>,
    val battleTally: Int,
    val battlesWon: Int,
    val requisitionPoints: Int,
    val supplyLimit: Int,
    val supplyUsed: Int,
    val crusadePoints: Int,
)