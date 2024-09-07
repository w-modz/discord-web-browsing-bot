plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("org.javacord:javacord:3.8.0")
    implementation("com.github.masecla22:Reddit4J:-SNAPSHOT")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.17.0")
    implementation(project(":connectors:reddit"))
    implementation(project(":domain"))
}

application {
    mainClass = "org.example.app.AppKt"
}
