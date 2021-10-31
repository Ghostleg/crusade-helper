package ru.malygin.crusade.service.mongo.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.malygin.crusade.model.OrderOfBattle

@Repository
interface OrderOfBattleRepository : CrudRepository<OrderOfBattle, String> {
    fun findOneOrNoneByFactionAndPlayerName(faction: String, playerName: String): OrderOfBattle?
}