val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.0"
}

group = "fpmi.by"
version = "0.0.1"
application {
    mainClass.set("fpmi.by.ApplicationKt")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.exposed:exposed:0.12.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.31.1")
    implementation("mysql:mysql-connector-java:8.0.24")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}