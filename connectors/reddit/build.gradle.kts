plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("com.github.masecla22:Reddit4J:-SNAPSHOT")
    implementation(project(":domain"))
}
