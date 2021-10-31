package ru.malygin.crusade.tbot.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.malygin.crusade.model.OrderOfBattle
import ru.malygin.crusade.service.IOrderOfBattleService
import ru.malygin.crusade.tbot.ChatInfoService
import ru.malygin.crusade.tbot.commands.TextCommand

@Service
class OrderOfBattleMenu : InputMessageHandler, CallbackHandler {

    @Autowired
    private lateinit var orderOfBattleService: IOrderOfBattleService

    @Autowired
    private lateinit var chatInfoService: ChatInfoService


    override fun handle(callback: CallbackQuery): SendMessage {
        val nickname = chatInfoService.getNickname(callback.message.chatId)
            ?: return SendMessage(callback.message.chatId.toString(), "Извини, я тебя не узнаю")

        val faction = callback.data.substringAfter("Load:OrderOfBattle:")

        return replyWithOrderOfBattle(nickname, faction)
            .apply { this.chatId = callback.message.chatId.toString() }
    }

    override fun handle(message: Message?): SendMessage {
        requireNotNull(message)

        return when (message.text) {
            TextCommand.OpenOrderOfBattle.commandText -> {
                replyWithOrderOfBattlesList(message)
            }
            TextCommand.StartCrusade.commandText -> {
                SendMessage()
            }
            else -> {
                SendMessage(message.chatId.toString(), "Я запутался")
            }
        }
    }

    private fun replyWithOrderOfBattlesList(message: Message) =
        SendMessage(message.chatId.toString(), "Выберите армию")
            .apply {
                this.replyMarkup = chatInfoService.getNickname(message.chatId)
                    ?.let {
                        orderOfBattleService.listPlayerOrderOfBattles(it)
                    }?.map {
                        it.faction
                    }?.let { orderOfBattlesKeyboard(it) }
            }

    private fun orderOfBattlesKeyboard(orderOfBattleNames: List<String>): InlineKeyboardMarkup {
        return InlineKeyboardMarkup()
            .apply {
                this.keyboard = orderOfBattleNames.map {
                    InlineKeyboardButton()
                        .apply { this.text = it }
                        .apply { this.callbackData = "Load:OrderOfBattle:$it" }
                        .let { listOf(it) }
                }
            }
    }

    private fun replyWithOrderOfBattle(nickname: String, faction: String) : SendMessage {
        val orderOfBattle = orderOfBattleService.getOrderOfBattle(nickname, faction) ?: return SendMessage().apply { this.text = "Упс, что-то пошло не так" }
        val orderOfBattleReply = SendMessage()

        orderOfBattleReply.text = "Faction : ${orderOfBattle.faction} \n" +
                "Battle Tally : ${orderOfBattle.battleTally}"
        orderOfBattleReply.replyMarkup = forceMarkup(orderOfBattle)

        return orderOfBattleReply
    }

    private fun forceMarkup(orderOfBattle: OrderOfBattle) : InlineKeyboardMarkup {
        return InlineKeyboardMarkup()
            .apply {
                this.keyboard = orderOfBattle.forces.map {
                    InlineKeyboardButton()
                        .apply { this.text = it.name }
                        .apply { this.callbackData = "Load:CrusadeUnit:${it.name}" }
                        .let { listOf(it) }
                }
            }
    }
}