package com.github.wmodz.discordwebbot.connectors.discord

import com.github.wmodz.discordwebbot.connectors.discord.listeners.SlashCommandListener
import com.github.wmodz.discordwebbot.domain.RedditConnector
import com.github.wmodz.discordwebbot.domain.SimpleRedditPost

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.intent.Intent
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.Integer.min

@Configuration
internal class DiscordConnectorConfig {

    @Value("\${discord-api.token}")
    private lateinit var token: String

    @Bean
    fun discordApi(
        redditConnector: RedditConnector,
        slashCommandListener: SlashCommandListener,
    ): DiscordApi {
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

        val redditCommand: SlashCommand =
        SlashCommand.with("reddit", "Gets top posts from specified subreddit",
                listOf(
                    SlashCommandOption.create(SlashCommandOptionType.STRING,
                        "subreddit",
                        "Subreddit to request posts from, without the 'r/'.",
                        true
                    )
                )
            )
                .createGlobal(api)
                .join()

        api.addSlashCommandCreateListener(slashCommandListener)

        return api
    }

    // Function for parsing post collection from "redditConnector.fetchTopPostsFrom" into a String
    fun parsePosts(posts: Collection<SimpleRedditPost>): String {
        var outputString: String = ""
        for (post in posts) {
            outputString += post.title + " " + post.thumbnailUri + "\n"
        }
        return outputString
            .substring(0, min(outputString.length, MAX_MSG_LENGTH))
    }

    private companion object {
        private const val MAX_MSG_LENGTH = 2000
    }
}
