package com.github.wmodz.discordwebbot.connectors.discord

import org.javacord.api.entity.server.Server
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionChoice
import org.javacord.api.interaction.SlashCommandOptionType
import org.springframework.stereotype.Component
import java.util.*

@Component
class RedditSlashCommandFactory() : SlashCommandFactory {
    override fun build(guild: Server) = SlashCommand.with(
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
        // Creating commands locally for testing; this should be implemented as global for production
        .createForServer(guild)

}