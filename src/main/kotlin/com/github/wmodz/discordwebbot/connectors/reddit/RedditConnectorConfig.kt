package com.github.wmodz.discordwebbot.connectors.reddit

import com.github.wmodz.discordwebbot.domain.RedditConnector
import masecla.reddit4j.client.Reddit4J
import masecla.reddit4j.client.UserAgentBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class RedditConnectorConfig {

    @Value("\${reddit-api.username}")
    private lateinit var username: String
    @Value("\${reddit-api.password}")
    private lateinit var password: String
    @Value("\${reddit-api.client-secret}")
    private lateinit var clientSecret: String
    @Value("\${reddit-api.client-id}")
    private lateinit var clientId: String

    @Bean
    fun redditClient(): Reddit4J =
        Reddit4J.rateLimited()
            .setUserAgent(UserAgentBuilder().author("").appname("").version(""))
            .setUsername(username)
            .setPassword(password)
            .setClientSecret(clientSecret)
            .setClientId(clientId)

    @Bean
    fun redditConnector(redditClient: Reddit4J): RedditConnector =
        DefaultRedditConnector(redditClient)
}
