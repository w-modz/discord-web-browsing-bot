package org.example.app

import com.github.wmodz.discordwebbot.connectors.reddit.DefaultRedditConnector
import masecla.reddit4j.client.Reddit4J
import masecla.reddit4j.client.UserAgentBuilder

fun main() {
    val token = ""
    /*val api = DiscordApiBuilder()
        .setToken(token)
        .addIntents(Intent.MESSAGE_CONTENT)
        .login()
        .join()

    api.addMessageCreateListener { event: MessageCreateEvent ->
        if (event.messageContent.equals("!ping", ignoreCase = true)) {
            event.channel.sendMessage("Pong!")
        }
    }*/
    redditHelloWorld()
}

fun redditHelloWorld() {
    val client = Reddit4J.rateLimited()
        .setUserAgent(UserAgentBuilder().author("").appname("").version(""))
        .setUsername("")
        .setPassword("")
        .setClientSecret("")
        .setClientId("")
    val redditConnector = DefaultRedditConnector(client)
    val subredditPosts = redditConnector.fetchTopPostsFrom("pics")
    println(subredditPosts.map { it.title })
}
