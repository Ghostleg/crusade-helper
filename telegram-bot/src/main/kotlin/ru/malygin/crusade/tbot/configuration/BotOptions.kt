package ru.malygin.crusade.tbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "crusadebot")
open class BotOptions {
    lateinit var botName: String
    lateinit var botToken: String
    lateinit var webHookPath: String
    lateinit var proxyType: String
    lateinit var proxyHost: String
    lateinit var proxyPort: String
}