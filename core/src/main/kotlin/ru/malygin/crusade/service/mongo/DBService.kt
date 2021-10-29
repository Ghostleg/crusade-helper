package ru.malygin.crusade.service.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.BsonObjectId
import org.bson.codecs.RepresentationConfigurable
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import ru.malygin.crusade.model.CrusadeUnit
import ru.malygin.crusade.model.OrderOfBattle

interface DBService {
    fun <T> addRuleEntity(entity: T, clazz: Class<T>, collectionName: String)
    fun <T> ruleByName(ruleName: String, clazz: Class<T>, collectionName: String): T?
    fun getUnit(unit: CrusadeUnit, orderOfBattle: OrderOfBattle): CrusadeUnit?
    fun updateUnitStats(unit: CrusadeUnit, orderOfBattle: OrderOfBattle)
    fun getOrderOfBattle(faction: String, playerNick: String): OrderOfBattle?
    fun getOrderOfBattle(id: ObjectId): OrderOfBattle?
    fun mergeOrderOfBattle(orderOfBattle: OrderOfBattle): OrderOfBattle?
    fun addUnitToOrderOfBattle(unit: CrusadeUnit, orderOfBattleId: ObjectId)
}


// TODO : Link objects via DBRef on deserialization

class MongoService : DBService {
    private val connectionString = ConnectionString("mongodb+srv://HelperAdmin:123@cluster0.eed7t.mongodb.net/crusade-helper-rules?retryWrites=true&w=majority")
    private val mongoClient: MongoClient
    private val database: MongoDatabase

    init {
        val pojoCodecRegistry = fromProviders(
            PojoCodecProvider.builder()
                .automatic(true)
                .build()
        )
        val codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)
        val settings: MongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build()
        mongoClient = MongoClients.create(settings)
        database = mongoClient.getDatabase("crusade-helper-rules")
    }

    override fun <T> addRuleEntity(entity: T, clazz: Class<T>, collectionName: String) {
        database.getCollection(collectionName, clazz)
            .insertOne(entity)
    }

    override fun <T> ruleByName(ruleName: String, clazz: Class<T>, collectionName: String): T? {
        return database.getCollection(collectionName, clazz)
            .find(eq("name", ruleName))
            .firstOrNull()
    }

    override fun getUnit(unit: CrusadeUnit, orderOfBattle: OrderOfBattle): CrusadeUnit? {
        return crusadeUnitsCollection(orderOfBattle)
            .find(eq("_id", unit._id))
            .firstOrNull()

    }

    override fun addUnitToOrderOfBattle(unit: CrusadeUnit, orderOfBattleId: ObjectId) {
        val playersOrderOfBattle = orderOfBattleCollection().find(eq("_id", orderOfBattleId)).firstOrNull()

        requireNotNull(playersOrderOfBattle)

        val insertedUnit = (crusadeUnitsCollection(playersOrderOfBattle).insertOne(unit).insertedId as? BsonObjectId)?.value

        requireNotNull(insertedUnit)

        mergeOrderOfBattle(playersOrderOfBattle.let { it.copy( forces = it.forces.toMutableList().apply { this.add(unit.copy(_id = insertedUnit)) }) })
    }

    override fun updateUnitStats(unit: CrusadeUnit, orderOfBattle: OrderOfBattle) {
        crusadeUnitsCollection(orderOfBattle)
            .findOneAndReplace(eq("_id", unit._id), unit)
    }

    override fun getOrderOfBattle(faction: String, playerNick: String): OrderOfBattle? {
        return orderOfBattleCollection()
            .find(and(eq("playerNickname", playerNick), eq("faction", faction)))
            .firstOrNull()
    }

    override fun getOrderOfBattle(id: ObjectId): OrderOfBattle? {
        return orderOfBattleCollection()
            .find(eq("_id", id))
            .firstOrNull()
    }

    override fun mergeOrderOfBattle(orderOfBattle: OrderOfBattle): OrderOfBattle? {
        return orderOfBattleCollection()
            .findOneAndReplace(
                //and(eq("playerNickname", orderOfBattle.playerNickname), eq("faction", orderOfBattle.faction)),
                eq("_id", orderOfBattle._id),
                orderOfBattle
            ) ?: orderOfBattleCollection().insertOne(orderOfBattle).insertedId?.let { orderOfBattleCollection().find(eq("_id", it)).firstOrNull() }
    }

/*
    private fun playerCrusadeUnits(orderOfBattle: OrderOfBattle) =
        database.getCollection("${orderOfBattle.playerNickname}-${orderOfBattle.faction}-crusade", CrusadeUnit::class.java)
*/

    private fun orderOfBattleCollection() = database.getCollection("crusade-ordersofbattle", OrderOfBattle::class.java)

    private fun crusadeUnitsCollection(orderOfBattle: OrderOfBattle) = database.getCollection("crusade-units", CrusadeUnit::class.java)

}

