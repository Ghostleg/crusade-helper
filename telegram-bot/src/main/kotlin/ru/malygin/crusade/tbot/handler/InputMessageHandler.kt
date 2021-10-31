package ru.malygin.crusade.tbot.handler

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

interface InputMessageHandler {
    fun handle(message: Message?): SendMessage
}