package com.github.wmodz.discordwebbot.connectors.discord

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.intent.Intent
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
internal class DiscordConnectorConfig {

    @Value("\${discord-api.token}")
    private lateinit var token: String

    // GuidID for test server
    @Value("\${discord-api.testserver-guild-id}")
    private lateinit var GuildID : String

    @Bean
    fun discordApi(): DiscordApi {
        val api = DiscordApiBuilder()
            .setToken(token)
            .addIntents(Intent.MESSAGE_CONTENT)
            .login()
            .join()

        val guild = api.getServerById(GuildID).get()
        guild.getSlashCommands().join().forEach { command ->
            command.deleteForServer(guild).join()
        }


        SlashCommand.with(
            "reddit",
            "Fetches posts from Reddit",
            Arrays.asList(
                SlashCommandOption.createWithChoices(
                    SlashCommandOptionType.STRING,
                    "sorting",
                    "What to sort the reddit posts by",
                    true, // required = true
                    Arrays.asList(
                        SlashCommandOptionChoice.create("TOP", "TOP"),
                        SlashCommandOptionChoice.create("HOT", "HOT"),
                        SlashCommandOptionChoice.create("NEW", "NEW")
                    )
                ),
                SlashCommandOption.create(
                    SlashCommandOptionType.STRING,
                    "subreddit",
                    "What subreddit to fetch posts from",
                    true
                )
            )
        )
            .createForServer(guild)
            .join()

        api.addSlashCommandCreateListener { event: SlashCommandCreateEvent ->
            val slashCommand = event.slashCommandInteraction

            if (slashCommand.commandName.equals("reddit", ignoreCase = true)) {
                val sorting = slashCommand.getOptionByName("sorting")
                    .flatMap { it.stringValue }
                    .orElse("UNKNOWN")

                val subreddit = slashCommand.getOptionByName("subreddit")
                    .flatMap { it.stringValue }
                    .orElse("UNKNOWN")

                slashCommand.createImmediateResponder()
                    .setContent("Fetching $sorting posts from $subreddit")
                    .respond()
            }
        }

        return api
    }
}
