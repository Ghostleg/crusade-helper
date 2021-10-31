package ru.malygin.crusade.service

import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle
import ru.malygin.crusade.service.mongo.DBService

interface IOrderOfBattleService {
    fun startCrusade(playerNickname: String, playerName: String, faction: String): StartCrusadeResult
    fun getOrderOfBattle(playerNickname: String, faction: String): OrderOfBattle?
    fun listPlayerOrderOfBattles(playerNickname: String): List<OrderOfBattle>
    fun addUnit(crusadeUnit: CrusadeUnit, orderOfBattle: OrderOfBattle): OrderOfBattle
}

class OrderOfBattleService(
    private val dbService: DBService
) : IOrderOfBattleService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderOfBattleService::class.java)
    }


    override fun startCrusade(playerNickname: String, playerName: String, faction: String): StartCrusadeResult {
        val orderOfBattle = dbService.getOrderOfBattle(faction, playerNickname)
        if (orderOfBattle != null) {
            return AlreadyStartedCrusade(orderOfBattle)
        }

        val newOrder = OrderOfBattle(
            ObjectId(),
            faction,
            playerName,
            playerNickname,
        )

        return dbService.mergeOrderOfBattle(newOrder)?.let { SuccessfullyStartedCrusade(it) }
            ?: FailedToStartCrusade().also { logger.warn("Sorry, can't create new order of battle.") }
    }

    override fun getOrderOfBattle(playerNickname: String, faction: String): OrderOfBattle? {
        return dbService.getOrderOfBattle(faction, playerNickname)
    }

    override fun listPlayerOrderOfBattles(playerNickname: String): List<OrderOfBattle> = dbService.getOrderOfBattles(playerNickname)


    override fun addUnit(crusadeUnit: CrusadeUnit, orderOfBattle: OrderOfBattle): OrderOfBattle {
        // TODO: 10/30/2021 fill crusadeUnit with codex parameters

        val inserted = dbService.addCrusadeUnit(crusadeUnit) ?: return orderOfBattle.also { logger.warn("Can't add unit to Database") }

        return dbService.addUnitToOrderOfBattle(inserted, orderOfBattle._id)
            ?: orderOfBattle.also { logger.warn("Failed to update orderOfBattle") }
    }
}