package ru.malygin.crusade.tbot.handler

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

interface CallbackHandler {
    fun handle(callback: CallbackQuery): SendMessage
}