package com.github.wmodz.discordwebbot

import com.github.wmodz.discordwebbot.domain.reddit.RedditConnector
import org.springframework.stereotype.Component

@Component
class ExampleInjection(private val redditConnector: RedditConnector) {
    fun foo(subredditName: String) =
        redditConnector.fetchTopPostsFrom(subredditName = subredditName)
}
