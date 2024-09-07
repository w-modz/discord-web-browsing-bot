plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("com.github.masecla22:Reddit4J:-SNAPSHOT")
    implementation(libs.spring.boot)
    implementation(project(":domain"))
}
