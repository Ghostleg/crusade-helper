package ru.malygin.crusade.tbot

import org.springframework.stereotype.Service

@Service
class ChatInfoService {
    private val chatIdToNickname = mutableMapOf<Long, String>()

    // TODO: 10/31/2021 save nicknames to DB

    fun getNickname(chatId: Long) = chatIdToNickname[chatId]

    fun updateNicknameForChatId(chatId: Long, nickname: String) {
        chatIdToNickname[chatId] = nickname
    }

    fun nicknameAlreadyTaken(nickname: String) : Boolean {
        return chatIdToNickname.values.any { it.equals(nickname, true) }
    }
}
