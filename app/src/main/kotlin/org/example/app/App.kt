package org.example.app

import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.intent.Intent
import org.javacord.api.event.message.MessageCreateEvent

fun main() {
    val token = ""
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
}
