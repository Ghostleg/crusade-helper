package ru.malygin.crusade.service.mongo

import org.bson.types.ObjectId
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle

interface DBService {
    fun updateUnitStats(unit: CrusadeUnit): CrusadeUnit?
    fun getOrderOfBattle(faction: String, playerNick: String): OrderOfBattle?
    fun getOrderOfBattles(playerNick: String): List<OrderOfBattle>
    fun mergeOrderOfBattle(orderOfBattle: OrderOfBattle): OrderOfBattle?
    fun addUnitToOrderOfBattle(unit: CrusadeUnit, orderOfBattleId: ObjectId): OrderOfBattle?
    fun addCrusadeUnit(unit: CrusadeUnit): CrusadeUnit?
}