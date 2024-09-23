package com.github.wmodz.discordwebbot.connectors.discord.handlers

import com.github.wmodz.discordwebbot.domain.RedditConnector

internal class RedditTopPostsHandler(
    private val redditConnector: RedditConnector,
) {

    // 1. [Title 1](uri1)
    // 2. [Title 2](uri2)
    // ...
    fun handle(): String {
        val topPosts = redditConnector.fetchTopPostsFrom("")
        return ""
    }
}
