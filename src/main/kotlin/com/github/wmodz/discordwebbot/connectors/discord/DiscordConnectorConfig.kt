package com.github.wmodz.discordwebbot.connectors.discord

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.intent.Intent
import org.javacord.api.event.message.MessageCreateEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class DiscordConnectorConfig {

    @Value("\${discord-api.token}")
    private lateinit var token: String

    @Bean
    fun discordApi(): DiscordApi {
        val api = DiscordApiBuilder()
            .setToken(token)
            .addIntents(Intent.MESSAGE_CONTENT)
            .login()
            .join()

        api.addMessageCreateListener { event: MessageCreateEvent ->
            if (event.messageContent.equals("!ping", ignoreCase = true)) {
                event.channel.sendMessage("Pong!")
            }
        }

        return api
    }
}
