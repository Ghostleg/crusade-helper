package ru.malygin.crusade.service.mongo

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.update
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle

class SpringDataMongoService(
    private val operations: MongoOperations
) : DBService {
    override fun updateUnitStats(unit: CrusadeUnit): CrusadeUnit? {
        return operations.update<CrusadeUnit>()
            .replaceWith(unit)
            .findAndReplaceValue()
    }

    override fun getOrderOfBattle(faction: String, playerNick: String): OrderOfBattle? {
        return operations.query<OrderOfBattle>()
            .matching(
                Criteria().andOperator(OrderOfBattle::faction isEqualTo faction, OrderOfBattle::playerNickname isEqualTo playerNick)
            )
            .oneValue()
    }

    override fun getOrderOfBattles(playerNick: String): List<OrderOfBattle> {
        return operations.query<OrderOfBattle>()
            .matching(
                where("playerNickname" ).isEqualTo(playerNick)
            )
            .all()
    }

    override fun mergeOrderOfBattle(orderOfBattle: OrderOfBattle): OrderOfBattle? {
        return operations.update<OrderOfBattle>()
            .replaceWith(orderOfBattle)
            .findAndReplaceValue()
    }

    override fun addUnitToOrderOfBattle(unit: CrusadeUnit, orderOfBattleId: ObjectId): OrderOfBattle? {
        return operations.update<OrderOfBattle>()
            .matching(query(where("_id").isEqualTo(orderOfBattleId)))
            .apply(Update().push("forces", unit))
            .findAndModifyValue()

    }

    override fun addCrusadeUnit(unit: CrusadeUnit): CrusadeUnit? = operations.insert(unit)

}