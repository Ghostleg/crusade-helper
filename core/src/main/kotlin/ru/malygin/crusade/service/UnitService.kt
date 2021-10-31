package ru.malygin.crusade.service

import org.slf4j.LoggerFactory
import ru.malygin.crusade.model.BattleExperience
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle
import ru.malygin.crusade.model.UnitBattleResult
import ru.malygin.crusade.service.mongo.DBService

interface IUnitService {
    fun registerBattleParticipation(unit: CrusadeUnit, battleResult: UnitBattleResult, orderOfBattle: OrderOfBattle)
    fun listPlayerUnits(faction: String, playerNick: String): List<CrusadeUnit>
    fun upgradeUnit(crusadeUnit: CrusadeUnit): CrusadeUnit
}

class UnitService(
    private val database: DBService
) : IUnitService {

    companion object {
        private val logger = LoggerFactory.getLogger(UnitService::class.java)
    }

    override fun registerBattleParticipation(unit: CrusadeUnit, battleResult: UnitBattleResult, orderOfBattle: OrderOfBattle) {
        val newExperience = battleExperience(unit.experience, battleResult)

        unit.copy(experience = newExperience)
            .let { database.updateUnitStats(it) }
    }

    override fun listPlayerUnits(faction: String, playerNick: String): List<CrusadeUnit> {
        val playersOrderOfBattle = database.getOrderOfBattle(faction, playerNick)

        if (playersOrderOfBattle == null) {
            logger.warn("Sorry can't find crusade order of battle for $playerNick's $faction")
            return emptyList()
        }

        return playersOrderOfBattle.forces
    }

    override fun upgradeUnit(crusadeUnit: CrusadeUnit): CrusadeUnit {
        return database.updateUnitStats(crusadeUnit)
            ?: return crusadeUnit.also { OrderOfBattleService.logger.warn("Failed to update Unit") }
    }

    private fun battleExperience(
        experience: BattleExperience,
        battleResult: UnitBattleResult
    ): BattleExperience {
        val unitsKilled = experience.unitsKilled + battleResult.unitsKilled
        val experienceGainedByUnitsKilled = unitsKilled - (experience.unitsKilled % 3)
        val exp = experienceGainedByUnitsKilled + 1 + (if (battleResult.markedForGreatness) 3 else 0)
        return experience.copy(
            unitsKilled = unitsKilled,
            battlesPlayed = experience.battlesPlayed + 1,
            battlesSurvived = experience.battlesSurvived + (if (battleResult.killedInAction) 0 else 1),
            markedForGreatness = experience.markedForGreatness + (if (battleResult.markedForGreatness) 1 else 0),
            experience = exp
        )
    }

}