package ru.malygin.crusade.service.mongo

import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle
import ru.malygin.crusade.model.codex.BattlefieldRole
import ru.malygin.crusade.model.codex.UnitCodexParameters
import ru.malygin.crusade.model.codex.UnitType
import java.lang.IllegalStateException

interface ICrusadeService {
    fun startCrusade(playerNickname: String, playerName: String, faction: String): StartCrusadeResult
    fun addUnit(orderOfBattle: OrderOfBattle)
}

class CrusadeService(
    private val dbService: DBService
) : ICrusadeService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CrusadeService::class.java)
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
            ?: FailedToStartCrusade().also { logger.warn("Sorry, can't create new order of battle.")}
    }

    override fun addUnit(orderOfBattle: OrderOfBattle) {
        val unit = CrusadeUnit(
            ObjectId(),
            "Some shit",
            codexParams = UnitCodexParameters(BattlefieldRole.Elite, 5),
            crusadeFaction = "Ultramarines",
            selectableKeywords = emptyList()
        )

        dbService.addUnitToOrderOfBattle(unit,orderOfBattle._id )
    }

}