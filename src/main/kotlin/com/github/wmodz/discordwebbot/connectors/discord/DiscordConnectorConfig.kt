package com.github.wmodz.discordwebbot.connectors.discord

import com.github.wmodz.discordwebbot.domain.RedditConnector
import masecla.reddit4j.objects.Sorting
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.intent.Intent
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.awt.Color
import java.util.concurrent.CompletableFuture


@Configuration
internal class DiscordConnectorConfig {

    @Value("\${discord-api.token}")
    private lateinit var token: String

    // GuidID for test server
    @Value("\${discord-api.testserver-guild-id}")
    private lateinit var GuildID : String

    @Bean
    fun discordApi(slashCommandFactories: Collection<SlashCommandFactory>, redditConnector: RedditConnector): DiscordApi {
        val api = DiscordApiBuilder()
            .setToken(token)
            .addIntents(Intent.MESSAGE_CONTENT)
            .login()
            .join()

        val guild = api.getServerById(GuildID).get()
        guild.getSlashCommands().join().forEach { command ->
            command.deleteForServer(guild).join()
        }

        val slashCommandsFutures = slashCommandFactories.map { it.build(guild) }
        CompletableFuture.allOf(
            *slashCommandsFutures.filterNotNull().toTypedArray<CompletableFuture<*>>()
        )

        api.addSlashCommandCreateListener { event ->
            val slashCommand = event.slashCommandInteraction

            if (slashCommand.commandName.equals("reddit", ignoreCase = true)) {
                val postSorting = slashCommand.getOptionByName("sorting")
                    .flatMap { it.stringValue }
                    .orElse("HOT")

                val subreddit = slashCommand.getOptionByName("subreddit")
                    .flatMap { it.stringValue }
                    .orElse("all")

                val posts = redditConnector.fetchTopPostsFrom(
                    subredditName = subreddit,
                    sorting = Sorting.valueOf(postSorting.uppercase())
                )

                val embeds = posts.take(5).map { post ->
                    val imageUrl = post.thumbnailUri.toString()

                    // Validate that it's a proper image link
                    val validImageUrl = if (
                        imageUrl.startsWith("http", ignoreCase = true) &&
                        (imageUrl.endsWith(".jpg") ||
                                imageUrl.endsWith(".png") ||
                                imageUrl.endsWith(".jpeg") ||
                                imageUrl.endsWith(".gif"))
                    ) imageUrl else null

                    EmbedBuilder()
                        .setTitle(post.title)
                        .setUrl("https://reddit.com${post.permalink}")
                        .apply {
                            if (validImageUrl != null) setImage(validImageUrl) // 👈 large image below title
                        }
                        .setColor(Color.ORANGE)
                }

                slashCommand.createImmediateResponder()
                    .setContent("📢 Top ${posts.size.coerceAtMost(5)} posts from r/$subreddit (${postSorting.uppercase()}):")
                    .addEmbeds(embeds)
                    .respond()
            }
        }


        return api
    }
}
