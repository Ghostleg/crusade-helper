package ru.malygin.crusade.service.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.BsonObjectId
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle

class MongoService /*: DBService*/ {
    private val connectionString = ConnectionString("mongodb+srv://HelperAdmin:123@cluster0.eed7t.mongodb.net/crusade-helper-rules?retryWrites=true&w=majority")
    private val mongoClient: MongoClient
    private val database: MongoDatabase

    init {
        val pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                .automatic(true)
                .build()
        )
        val codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)
        val settings: MongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build()
        mongoClient = MongoClients.create(settings)
        database = mongoClient.getDatabase("crusade-helper-rules")
    }

    fun <T> addRuleEntity(entity: T, clazz: Class<T>, collectionName: String) {
        database.getCollection(collectionName, clazz)
            .insertOne(entity)
    }

    fun <T> ruleByName(ruleName: String, clazz: Class<T>, collectionName: String): T? {
        return database.getCollection(collectionName, clazz)
            .find(Filters.eq("name", ruleName))
            .firstOrNull()
    }

    fun getUnit(unit: CrusadeUnit, orderOfBattle: OrderOfBattle): CrusadeUnit? {
        return crusadeUnitsCollection()
            .find(Filters.eq("_id", unit._id))
            .firstOrNull()

    }

     fun addUnitToOrderOfBattle(unit: CrusadeUnit, orderOfBattleId: ObjectId): OrderOfBattle? {
        val playersOrderOfBattle = orderOfBattleCollection().find(Filters.eq("_id", orderOfBattleId)).firstOrNull()

        requireNotNull(playersOrderOfBattle)

        val insertedUnit = (crusadeUnitsCollection().insertOne(unit).insertedId as? BsonObjectId)?.value

        requireNotNull(insertedUnit)

        return mergeOrderOfBattle(playersOrderOfBattle.let { it.copy(forces = it.forces.toMutableList().apply { this.add(unit.copy(_id = insertedUnit)) }) })
    }

    fun updateUnitStats(unit: CrusadeUnit): CrusadeUnit? {
        return crusadeUnitsCollection()
            .findOneAndReplace(Filters.eq("_id", unit._id), unit)
    }

     fun getOrderOfBattle(faction: String, playerNick: String): OrderOfBattle? {
        return orderOfBattleCollection()
            .find(Filters.and(Filters.eq("playerNickname", playerNick), Filters.eq("faction", faction)))
            .firstOrNull()
    }

    fun getOrderOfBattle(id: ObjectId): OrderOfBattle? {
        return orderOfBattleCollection()
            .find(Filters.eq("_id", id))
            .firstOrNull()
    }

     fun mergeOrderOfBattle(orderOfBattle: OrderOfBattle): OrderOfBattle? {
        return orderOfBattleCollection()
            .findOneAndReplace(
                //and(eq("playerNickname", orderOfBattle.playerNickname), eq("faction", orderOfBattle.faction)),
                Filters.eq("_id", orderOfBattle._id),
                orderOfBattle
            ) ?: orderOfBattleCollection().insertOne(orderOfBattle).insertedId?.let { orderOfBattleCollection().find(Filters.eq("_id", it)).firstOrNull() }
    }

/*
    private fun playerCrusadeUnits(orderOfBattle: OrderOfBattle) =
        database.getCollection("${orderOfBattle.playerNickname}-${orderOfBattle.faction}-crusade", CrusadeUnit::class.java)
*/

    private fun orderOfBattleCollection() = database.getCollection("orderOfBattle", OrderOfBattle::class.java)

    private fun crusadeUnitsCollection() = database.getCollection("crusadeUnit", CrusadeUnit::class.java)

}