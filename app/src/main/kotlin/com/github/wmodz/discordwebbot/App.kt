package com.github.wmodz.discordwebbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
internal open class DiscordWebBot

fun main(args: Array<String>) {
    val context = runApplication<DiscordWebBot>(*args)

    val subredditPosts = context.getBean(ExampleInjection::class.java)
        .foo("pics")
    println(subredditPosts.map { it.title })

//    val token = ""
    /*val api = DiscordApiBuilder()
        .setToken(token)
        .addIntents(Intent.MESSAGE_CONTENT)
        .login()
        .join()

    api.addMessageCreateListener { event: MessageCreateEvent ->
        if (event.messageContent.equals("!ping", ignoreCase = true)) {
            event.channel.sendMessage("Pong!")
        }
    }*/
}
