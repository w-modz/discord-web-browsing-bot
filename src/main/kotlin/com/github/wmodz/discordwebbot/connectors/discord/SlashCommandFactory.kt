package com.github.wmodz.discordwebbot.connectors.discord

import org.javacord.api.entity.server.Server
import org.javacord.api.interaction.SlashCommand
import java.util.concurrent.CompletableFuture

interface SlashCommandFactory {
    fun build(guild: Server): CompletableFuture<SlashCommand>?
}