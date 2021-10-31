package ru.malygin.crusade.tbot.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.malygin.crusade.tbot.ChatInfoService
import ru.malygin.crusade.tbot.commands.TextCommand
import ru.malygin.crusade.tbot.markup.okButton

@Service
class MainMenu : InputMessageHandler {
    @Autowired
    private lateinit var chatInfo: ChatInfoService


    override fun handle(message: Message?): SendMessage {
        requireNotNull(message)
        val playerNickname = chatInfo.getNickname(message.chatId)

        return if (playerNickname == null && message.isCommand && message.text == "/start") {
            SendMessage(message.chatId.toString(), "Добро Пожаловать. Введите свой никнейм")
        } else if (playerNickname == null && chatInfo.nicknameAlreadyTaken(message.text)) {
            SendMessage(message.chatId.toString(), "Извините никнейм уже занят. Введите другой.")
        } else {
            if (playerNickname == null) {
                chatInfo.updateNicknameForChatId(message.chatId, message.text)
            }

            SendMessage(message.chatId.toString(), "Welcome ${playerNickname ?: message.text} !")
                .apply { this.replyMarkup = getKeyboard() }
        }
    }

    private fun getKeyboard(): ReplyKeyboardMarkup {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()

        val startCrusade = KeyboardButton()
            .apply { this.text = TextCommand.StartCrusade.commandText }

        val openCrusade = KeyboardButton()
            .apply { this.text = TextCommand.OpenOrderOfBattle.commandText }

        val back = KeyboardButton()
            .apply { this.text = TextCommand.Back.commandText }

        replyKeyboardMarkup.keyboard = listOf(
            KeyboardRow()
                .apply { add(startCrusade) }
                .apply { add(openCrusade) },
            KeyboardRow()
                .apply { add(back) }
        )

        return replyKeyboardMarkup
    }
}