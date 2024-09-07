package org.example.app

import com.github.wmodz.discordwebbot.connectors.reddit.Foo
import masecla.reddit4j.client.Reddit4J
import masecla.reddit4j.client.UserAgentBuilder
import masecla.reddit4j.objects.Sorting

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
    Foo()
}

fun redditHelloWorld() {
    val client = Reddit4J.rateLimited()
    client.setUserAgent(UserAgentBuilder().author("").appname("").version(""))
    client.setUsername("").setPassword("").setClientSecret("").setClientId("")
    val subredditPosts = client.getSubredditPosts("pics", Sorting.NEW).submit()
    println(subredditPosts.map { it.title })
}
