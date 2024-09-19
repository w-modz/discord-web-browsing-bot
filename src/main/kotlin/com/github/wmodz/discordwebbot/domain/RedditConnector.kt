package com.github.wmodz.discordwebbot.domain

import java.net.URI

interface RedditConnector {
    fun fetchTopPostsFrom(subredditName: String): Collection<SimpleRedditPost>
}

data class SimpleRedditPost(
    val title: String,
    val permalink: URI,
    val thumbnailUri: URI,
)
