package ru.malygin.crusade.tbot.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.bots.DefaultBotOptions
import ru.malygin.crusade.service.IOrderOfBattleService
import ru.malygin.crusade.service.IUnitService
import ru.malygin.crusade.tbot.CrusadeFacade
import ru.malygin.crusade.tbot.CrusadeHelperBot
import ru.malygin.crusade.tbot.ICrusadeFacade
import ru.malygin.crusade.tbot.handler.MainMenu

@Configuration
@EnableConfigurationProperties(BotOptions::class)
open class BotConfiguration {

    @Bean
    open fun crusadeFacade(
        orderOfBattleService: IOrderOfBattleService,
        unitService: IUnitService,
    ): ICrusadeFacade {
        return CrusadeFacade(orderOfBattleService, unitService)
    }


    @Bean
    open fun crusadeHelperBot(
        botOptions: BotOptions,
        crusadeFacade: ICrusadeFacade,
    ): CrusadeHelperBot {
        return CrusadeHelperBot(
            botOptions.botName,
            botOptions.botToken,
            botOptions.webHookPath,
            crusadeFacade,
            DefaultBotOptions()
                .apply {
                    this.proxyHost = botOptions.proxyHost
                    this.proxyPort = botOptions.proxyPort.toInt()
                    this.proxyType = DefaultBotOptions.ProxyType.valueOf(botOptions.proxyType)
                }
        )
    }


}