package ru.malygin.crusade.tbot.markup

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

fun okButton () = InlineKeyboardMarkup()
    .apply {
        keyboard = listOf(listOf(InlineKeyboardButton()
            .apply { this.text = "Ok" }
            .apply { this.callbackData = "Ok" }))
    }

