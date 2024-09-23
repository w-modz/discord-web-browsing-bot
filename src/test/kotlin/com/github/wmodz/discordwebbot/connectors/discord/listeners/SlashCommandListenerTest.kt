package com.github.wmodz.discordwebbot.connectors.discord.listeners

import com.github.wmodz.discordwebbot.connectors.discord.handlers.RedditTopPostsHandler
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommandInteraction
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class SlashCommandListenerTest {

    @Mock
    private lateinit var interaction: SlashCommandInteraction
    @Mock
    private lateinit var slashCommandCreateEvent: SlashCommandCreateEvent
    @Mock
    private lateinit var immediateResponseBuilder: InteractionImmediateResponseBuilder
    @Mock
    private lateinit var redditTopPostsHandler: RedditTopPostsHandler

    private lateinit var slashCommandListener: SlashCommandListener

    @BeforeEach
    fun setUp() {
        slashCommandListener = SlashCommandListener(redditTopPostsHandler)
    }

    @Test
    fun `does nothing for unrecognised commands`() {
        `when`(slashCommandCreateEvent.slashCommandInteraction).thenReturn(interaction)
        `when`(interaction.fullCommandName).thenReturn("abcd")

        slashCommandListener.onSlashCommandCreate(slashCommandCreateEvent)

        verifyNoMoreInteractions(interaction)
    }

    @Test
    fun `handles reddit command`() {
        val discordMessage = "Foo Bar"
        `when`(slashCommandCreateEvent.slashCommandInteraction).thenReturn(interaction)
        `when`(interaction.fullCommandName).thenReturn("reddit")
        `when`(interaction.createImmediateResponder()).thenReturn(immediateResponseBuilder)
        `when`(redditTopPostsHandler.handle()).thenReturn(discordMessage)
        `when`(immediateResponseBuilder.setContent(discordMessage))
            .thenReturn(immediateResponseBuilder)

        slashCommandListener.onSlashCommandCreate(slashCommandCreateEvent)

        verify(immediateResponseBuilder).respond()
    }
}
