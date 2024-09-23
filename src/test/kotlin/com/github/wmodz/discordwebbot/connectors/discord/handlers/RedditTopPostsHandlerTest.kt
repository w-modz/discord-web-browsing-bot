package com.github.wmodz.discordwebbot.connectors.discord.handlers

import com.github.wmodz.discordwebbot.domain.RedditConnector
import com.github.wmodz.discordwebbot.domain.SimpleRedditPost
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.net.URI

@ExtendWith(MockitoExtension::class)
internal class RedditTopPostsHandlerTest {

    @Mock
    private lateinit var redditConnector: RedditConnector

    private lateinit var handler: RedditTopPostsHandler

    @BeforeEach
    fun setUp() {
        handler = RedditTopPostsHandler(redditConnector)
    }

    @Test
    fun `handles empty post collection`() {
        `when`(redditConnector.fetchTopPostsFrom(anyString())).thenReturn(emptyList())

        val discordMessage = handler.handle()

        assertThat(discordMessage).isEmpty()
    }

    @Test
    fun `returns message containing top Reddit posts`() {
        val title1 = "Title 1"
        val title2 = "Title 2"
        val topPosts = listOf(
            SimpleRedditPost(
                title = title1,
                permalink = URI("http://localhost:1234/posts/1"),
                thumbnailUri = URI("http://localhost:1234/posts/1.jpg"),
            ),
            SimpleRedditPost(
                title = title2,
                permalink = URI("http://localhost:1234/posts/2"),
                thumbnailUri = URI("http://localhost:1234/posts/2.jpg"),
            ),
        )
        `when`(redditConnector.fetchTopPostsFrom(anyString())).thenReturn(topPosts)

        val discordMessage = handler.handle()

        assertThat(discordMessage).contains(title1, title2)
    }
}
