package com.github.wmodz.discordwebbot.connectors.reddit

import com.github.wmodz.discordwebbot.domain.RedditConnector
import com.github.wmodz.discordwebbot.domain.SimpleRedditPost
import masecla.reddit4j.client.Reddit4J
import masecla.reddit4j.objects.RedditPost
import masecla.reddit4j.objects.Sorting
import java.net.URI

class DefaultRedditConnector(private val redditClient: Reddit4J) : RedditConnector {

    override fun fetchTopPostsFrom(subredditName: String): Collection<SimpleRedditPost> =
        redditClient
            .getSubredditPosts(subredditName, Sorting.TOP)
            .submit()
            .toSimplePosts()

    private companion object {
        private fun Collection<RedditPost>.toSimplePosts(): Collection<SimpleRedditPost> = this.map {
            SimpleRedditPost(
                title = it.title,
                permalink = URI(it.permalink),
                thumbnailUri = URI(it.thumbnail),
            )
        }
    }
}
