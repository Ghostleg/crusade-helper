package ru.malygin.crusade.tbot

import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.malygin.crusade.service.IOrderOfBattleService
import ru.malygin.crusade.service.IUnitService

class CrusadeHelperBot(
    private val botName: String,
    private val token: String,
    private val path: String,
    private val crusadeFacade: ICrusadeFacade,
    defaultBotOptions: DefaultBotOptions,
) : TelegramWebhookBot(defaultBotOptions) {

    override fun getBotToken(): String {
        return token
    }

    override fun getBotUsername(): String {
        return botName
    }

    override fun getBotPath(): String {
        return path
    }

    override fun onWebhookUpdateReceived(update: Update?): BotApiMethod<*> {
        requireNotNull(update)

        return crusadeFacade.handle(update)
    }
}