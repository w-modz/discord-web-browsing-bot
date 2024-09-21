package com.github.wmodz.discordwebbot.connectors.discord

import com.github.wmodz.discordwebbot.domain.RedditConnector
// Don't know if this is the right way to do this
import com.github.wmodz.discordwebbot.domain.SimpleRedditPost

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.intent.Intent
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.jvm.optionals.getOrElse

@Configuration
internal class DiscordConnectorConfig {

    @Value("\${discord-api.token}")
    private lateinit var token: String

    @Bean
    fun discordApi(redditConnector: RedditConnector): DiscordApi {
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

        // Listener for redditCommand
        api.addSlashCommandCreateListener { event: SlashCommandCreateEvent ->
            if (event.slashCommandInteraction.fullCommandName.equals("reddit", ignoreCase = true)) {
                event.interaction
                    .createImmediateResponder()
                    //.setContent(event.slashCommandInteraction.options[0].stringValue.getOrElse {""})
                    //.setContent(redditConnector.fetchTopPostsFrom(event.slashCommandInteraction.options[1].toString()).toString())
                    /*.setContent(redditConnector.fetchTopPostsFrom(
                        event.slashCommandInteraction.options[0].stringValue.getOrElse {""}).elementAt(0).title
                    )*/
                    .setContent(
                        parsePosts(
                            redditConnector.fetchTopPostsFrom(
                                event.slashCommandInteraction.options[0].stringValue.getOrElse {""}
                            )
                        )
                    )
                    .respond()
            }
        }



        return api
    }

    // Function for parsing post collection from "redditConnector.fetchTopPostsFrom" into a String
    fun parsePosts(posts: Collection<SimpleRedditPost>): String {
        var outputString: String = ""
        for (post in posts) {
            outputString += post.title + " " + post.thumbnailUri + "\n"
        }
        return outputString
    }
}
