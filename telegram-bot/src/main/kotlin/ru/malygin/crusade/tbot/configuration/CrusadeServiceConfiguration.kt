package ru.malygin.crusade.tbot.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import ru.malygin.crusade.service.IOrderOfBattleService
import ru.malygin.crusade.service.IUnitService
import ru.malygin.crusade.service.OrderOfBattleService
import ru.malygin.crusade.service.UnitService
import ru.malygin.crusade.service.mongo.DBService
import ru.malygin.crusade.service.mongo.SpringDataMongoService

@Configuration
@EnableMongoRepositories
open class CrusadeServiceConfiguration {


    @Bean
    open fun springDataMongoService(mongoOperations: MongoOperations): DBService {
        return SpringDataMongoService(mongoOperations)
    }

    @Bean
    open fun orderOfBattleService(
        database: DBService
    ): IOrderOfBattleService {
        return OrderOfBattleService(database)
    }

    @Bean
    open fun unitService(
        database: DBService
    ): IUnitService {
        return UnitService(database)
    }
}