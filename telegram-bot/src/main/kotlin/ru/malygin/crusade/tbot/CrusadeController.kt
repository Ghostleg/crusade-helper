package ru.malygin.crusade.tbot

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.malygin.crusade.tbot.CrusadeHelperBot

@RestController
class CrusadeController(
    private val telegramBot: CrusadeHelperBot
) {

    @RequestMapping(value = ["/"], method = [RequestMethod.POST])
    fun onUpdateReceived(@RequestBody update: Update?): BotApiMethod<*> {
        return telegramBot.onWebhookUpdateReceived(update)
    }
}