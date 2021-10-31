package ru.malygin.crusade.tbot

import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.malygin.crusade.service.IOrderOfBattleService
import ru.malygin.crusade.service.IUnitService
import ru.malygin.crusade.tbot.commands.TextCommand
import ru.malygin.crusade.tbot.handler.MainMenu
import ru.malygin.crusade.tbot.handler.OrderOfBattleMenu

interface ICrusadeFacade {
    fun handle(update: Update): BotApiMethod<*>
}

class CrusadeFacade(
    private val orderOfBattleService: IOrderOfBattleService,
    private val unitService: IUnitService,
) : ICrusadeFacade{

    @Autowired
    private lateinit var mainMenu: MainMenu

    @Autowired
    private lateinit var orderOfBattleMenu: OrderOfBattleMenu


    override fun handle(update: Update): BotApiMethod<*> {
        if (update.message?.text == TextCommand.OpenOrderOfBattle.commandText
            || update.message?.text == TextCommand.StartCrusade.commandText) {
            return orderOfBattleMenu.handle(update.message)
        }

        if (update.hasCallbackQuery()) {
            return orderOfBattleMenu.handle(update.callbackQuery)
        }

        return mainMenu.handle(update.message)
    }
}
