rootProject.name = "food-ordering-system"

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}

include("order-service")
include("order-service:order-application")
findProject(":order-service:order-application")?.name = "order-application"
include("order-service:order-container")
findProject(":order-service:order-container")?.name = "order-container"
include("order-service:order-dataaccess")
findProject(":order-service:order-dataaccess")?.name = "order-dataaccess"
include("order-service:order-domain")
findProject(":order-service:order-domain")?.name = "order-domain"
include("order-service:order-domain:order-domain-core")
findProject(":order-service:order-domain:order-domain-core")?.name = "order-domain-core"
include("order-service:order-domain:order-domain-service")
findProject(":order-service:order-domain:order-domain-service")?.name = "order-domain-service"
include("order-service:order-messaging")
findProject(":order-service:order-messaging")?.name = "order-messaging"
include("common")
include("common:common-domain")
findProject(":common:common-domain")?.name = "common-domain"
include("order-service:order-domain:order-application-service")
findProject(":order-service:order-domain:order-application-service")?.name = "order-application-service"
include("order-service:order-domain:order-application-service")
findProject(":order-service:order-domain:order-application-service")?.name = "order-application-service"
include("infrastructure")
include("infrastructure:kafka")
findProject(":infrastructure:kafka")?.name = "kafka"
include("infrastructure:kafka:kafka-config-data")
findProject(":infrastructure:kafka:kafka-config-data")?.name = "kafka-config-data"
include("infrastructure:kafka:kafka-consumer")
findProject(":infrastructure:kafka:kafka-consumer")?.name = "kafka-consumer"
include("infrastructure:kafka:kafka-model")
findProject(":infrastructure:kafka:kafka-model")?.name = "kafka-model"
include("infrastructure:kafka:kafka-producer")
findProject(":infrastructure:kafka:kafka-producer")?.name = "kafka-producer"
include("infrastructure:kafka:kafka-model")
findProject(":infrastructure:kafka:kafka-model")?.name = "kafka-model"
