package com.github.wmodz.discordwebbot.connectors.discord.listeners

import com.github.wmodz.discordwebbot.connectors.discord.handlers.RedditTopPostsHandler
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.listener.interaction.SlashCommandCreateListener

internal class SlashCommandListener(
    private val redditTopPostsHandler: RedditTopPostsHandler,
) : SlashCommandCreateListener {

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent) {
        when (event.slashCommandInteraction.fullCommandName) {
            "reddit" -> event.slashCommandInteraction
                .createImmediateResponder()
                .setContent(redditTopPostsHandler.handle())
                .respond()
        }
    }
}
