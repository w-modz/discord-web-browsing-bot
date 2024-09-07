plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("org.javacord:javacord:3.8.0")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.17.0")
    implementation(libs.spring.boot)
    implementation(project(":connectors:reddit"))
    implementation(project(":domain"))
}

application {
    mainClass = "com.github.wmodz.discordwebbot.AppKt"
}
