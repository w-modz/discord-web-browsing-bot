package com.github.wmodz.discordwebbot.domain

import masecla.reddit4j.objects.Sorting
import java.net.URI

interface RedditConnector {
    fun fetchTopPostsFrom(subredditName: String, sorting: Sorting): Collection<SimpleRedditPost>
}

data class SimpleRedditPost(
    val title: String,
    val permalink: URI,
    val thumbnailUri: URI,
)
