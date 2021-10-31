package ru.malygin.crusade.tbot.commands

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.malygin.crusade.service.IOrderOfBattleService
import ru.malygin.crusade.service.IUnitService

class DefaultCommand(
    private val orderOfBattleService: IOrderOfBattleService,
    private val unitService: IUnitService,
    private val description: String? = "",
) : IBotCommand {
    override fun getCommandIdentifier(): String {
        return "StartCrusade"
    }

    override fun getDescription(): String {
        return "Init OrderOfBattle and start your crusade!"
    }

    override fun processMessage(absSender: AbsSender?, message: Message?, arguments: Array<out String>?) {
       
    }
}